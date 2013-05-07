package edu.asu.krypton.controllers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.text.WordUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.krypton.model.repository.DataAccessObject;
import edu.asu.krypton.service.SessionDependant;

@RequestMapping("/scaffold")
@org.springframework.stereotype.Controller
public class ScaffoldController extends Controller {

	private final String DEFAULT_BODIES_DIR = "bodies/";
	private final String SCAFFOLD_DIR = "scaffold/";
	private final String DEFAULT_BODY = SCAFFOLD_DIR+"rows";
	private final String MAIN_BODY = SCAFFOLD_DIR+"entities";
	
	@Autowired(required=true)
	private DataAccessObject<?> dao;
	
	@Autowired(required=true)
	private SessionFactory sessionFactory;
	
	@RequestMapping(method=RequestMethod.GET,value="")
	public String getAllEntities(Model model,HttpServletRequest request){
	 Map<String, ClassMetadata>  map = (Map<String, ClassMetadata>) sessionFactory.getAllClassMetadata();
	 List<String> entities = new ArrayList<String>();
	 for(String entityName : map.keySet()){
	     String[] segments = entityName.split("\\.");//get simple name
	     entities.add(segments[segments.length-1]);
	 }
	 model.addAttribute("entities", entities);
	 return appropriateView(request, DEFAULT_BODIES_DIR+MAIN_BODY, defaultView(model,MAIN_BODY));
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/{entity}")
	@SessionDependant
	public String getDefault(Model model,
			HttpServletRequest request,
			@PathVariable String entity,
			@RequestParam(required=false,defaultValue="0") Long id,
			@RequestParam(required=false,defaultValue="") String ownerType,
			@RequestParam(required=false,defaultValue="0") Long ownerId) throws ClassNotFoundException{
		try {
		    Criteria criteria;
		    List<?> queryResult = null;
		    Class<?> entityClass = Class.forName("edu.asu.krypton.model.persist.db."+entity);
		    if(ownerId>0){
		    	Class<?> ownerTypeClass = Class.forName("edu.asu.krypton.model.persist.db."+ownerType);
		    	criteria = dao.getSession().createCriteria(ownerTypeClass)
		    			.add(Restrictions.eq("id", ownerId));
		    	String fieldName = null;
		    	
		    	for(Field field : getAllFields(ownerTypeClass)){
		    		if(field.getType().getSimpleName().equals("Collection")
		    		&& ((Class<?>)((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0]).getSimpleName().equals(entity)) {
		    			fieldName = field.getName();
		    			break;
		    		}
		    	}
		    	if (fieldName!=null){
		    		Method method;
		    		do{
		    			try{
		    				method = ownerTypeClass.getDeclaredMethod("get"+WordUtils.capitalize(fieldName));
		    			}catch (Exception e) {
							method = null;
							ownerTypeClass = ownerTypeClass.getSuperclass();
							if(ownerTypeClass == Object.class) break;
						}
		    		}while(method == null);
		    		method.setAccessible(true);
		    		queryResult = (List<?>) method.invoke(criteria.uniqueResult());
		    	}
		    	//3alashan el lazy loading
		    	//invoke all getters
		    	//TODO: this is a work around we should find a better way to do this
		    	for(Object obj : queryResult){
		    		for(Method method: obj.getClass().getDeclaredMethods()){
		    			if(method.getName().indexOf("get") == 0){
		    				method.invoke(obj);
		    			}
		    		}
		    	}
		    } else {
		    	criteria = dao.getSession().createCriteria(entityClass);
		    	if(id>0) criteria.add(Restrictions.eq("id", id));
		    	queryResult = criteria.list();
		    }   

			model.addAttribute("entityClassFields",getAllFields(entityClass))
				 .addAttribute("entities", queryResult)
				 .addAttribute("entityClassName", entityClass.getSimpleName());
			System.out.println("this is a real entity");
		} catch (Exception e) {
			System.out.println(entity +" is not a valid entity");
			e.printStackTrace();
		}
		return appropriateView(request, DEFAULT_BODIES_DIR+DEFAULT_BODY, defaultView(model,DEFAULT_BODY));
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="{entity}/{id}")
	@SessionDependant
	@Transactional
	//this is a critical section
	public @ResponseBody synchronized void delete(
			@PathVariable String entity,
			@PathVariable Long id) throws ClassNotFoundException{
		Class<?> entityClass = Class.forName("edu.asu.krypton.model.persist.db."+entity);
		dao.getSession().delete(dao.getSession().load(entityClass, id));
	}
	
	private List<Field> getAllFields(Class<?> entity){
		List<Field> result = new ArrayList<Field>();
	    Class<?> dbEntity = entity;
	    while (dbEntity != null && dbEntity != Object.class) {
	    	for(Field field:dbEntity.getDeclaredFields())
	    		result.add(field);
	        dbEntity = dbEntity.getSuperclass();
	    }
	    return result;
	}
}
