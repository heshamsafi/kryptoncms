package edu.asu.krypton.controllers;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wiztools.paginationlib.PaginatedItems;
import org.wiztools.paginationlib.PaginationUtil;

@RequestMapping("/scaffold")
@org.springframework.stereotype.Controller
public class ScaffoldController extends Controller {
	private final String DEFAULT_BODIES_DIR = "bodies/";
	private final String SCAFFOLD_DIR = "scaffold/";
	private final String DEFAULT_BODY = SCAFFOLD_DIR+"rows";
	private final String MAIN_BODY = SCAFFOLD_DIR+"entities";
	
	@Autowired(required=true)
	private MongoTemplate mongoTemplate;
	
	@RequestMapping(method=RequestMethod.GET,value="")
	public String getAllEntities(Model model,HttpServletRequest request) throws IOException, ClassNotFoundException{
	 Collection<String> entities = new ArrayList<String>();
	 for(Class docName : findDocuments("edu.asu.krypton.model.persist.db"))
	     entities.add(docName.getSimpleName());

	 model.addAttribute("entities", entities);
	 return appropriateView(request, DEFAULT_BODIES_DIR+MAIN_BODY, defaultView(model,MAIN_BODY));
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/{entity}")
	public String getDefault(Model model,
			HttpServletRequest request,
			@PathVariable String entity,
			@RequestParam(required=false,defaultValue="") String id,
			@RequestParam(required=false,defaultValue="") String ownerType,
			@RequestParam(required=false,defaultValue="") String ownerId,
			@RequestParam(required=false,defaultValue="1") int pageNo,
			@RequestParam(required=false,defaultValue="30") int pageSize) throws ClassNotFoundException{
		long totalSize = 0;
		try {
			
		    Query query = new Query();
		    Collection<?> queryResult = null;
		    Class<?> entityClass = Class.forName("edu.asu.krypton.model.persist.db."+entity);
		    if(!ownerId.equals("")){
		    	Class<?> ownerTypeClass = Class.forName("edu.asu.krypton.model.persist.db."+ownerType);
		    	query.addCriteria(Criteria.where("id").is(ownerId));
		    	String fieldName = null;
		    	
		    	for(Field field : getAllFields(ownerTypeClass)){
		    		if(field.getType().getSimpleName().equals("Collection")
		    		&& ((Class<?>)((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0]).getSimpleName().equals(entity)) {
		    			fieldName = field.getName();
		    			break;
		    		}
		    	}
		    	Class temp = ownerTypeClass;
		    	if (fieldName!=null){
		    		Method method;
		    		do{
		    			try{
		    				method = temp.getDeclaredMethod("get"+WordUtils.capitalize(fieldName));
		    			}catch (Exception e) {
							method = null;
							temp = temp.getSuperclass();
							if(temp == Object.class) break;
						}
		    		}while(method == null);
		    		method.setAccessible(true);
		    		queryResult = (Collection<?>) method.invoke(mongoTemplate.findOne(query, ownerTypeClass));
		    	}
		    	//i know this next line sucks ... and if you don't know why it does i will tell you
		    	//paginating directly from database using mongodb's indexes is way faster than fetching every thing into the memory and then 
		    	//manipulating that collections inside the memory ... if the entire collection is larger than the available memory size (god forbid)
		    	//the server will crash and burn :)
		    	//but i don't know how to paginate a dbref using mongo ... and if you do please change this next line and let me know how you did it :)
		    	totalSize = queryResult.size();
		    	queryResult = PaginationUtil.getPaginatedItems(queryResult, pageSize, pageNo).getPaginatedItems();
		    } else {
		    	query = new Query();
		    	Query counterQuery = new Query();
		    	query.limit(pageSize);
		    	query.skip(pageSize*(pageNo-1));
		    	if(!id.equals("")){
		    		 Criteria criteria = Criteria.where("id").is(id);
		    		 query.addCriteria(criteria);
		    		 counterQuery.addCriteria(criteria);
		    	}
		    	totalSize = mongoTemplate.count(counterQuery, entityClass);
		    	queryResult = mongoTemplate.find(query, entityClass);
		    }   

			model.addAttribute("entityClassFields",getAllFields(entityClass))
				 .addAttribute("entities", queryResult)
				 .addAttribute("entityClassName", entityClass.getSimpleName());
			System.out.println("this is a real entity");
		} catch (Exception e) {
			System.out.println(entity +" is not a valid entity");
			e.printStackTrace();
		}
		model.addAttribute("totalSize",totalSize)
			 .addAttribute("pageSize",pageSize)
			 .addAttribute("pageNo",pageNo);
		return appropriateView(request, DEFAULT_BODIES_DIR+DEFAULT_BODY, defaultView(model,DEFAULT_BODY));
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="{entity}/{id}")
	//this is a critical section
	public @ResponseBody //synchronized 
			void delete(
			@PathVariable String entity,
			@PathVariable String id) throws ClassNotFoundException{
		Class<?> entityClass = Class.forName("edu.asu.krypton.model.persist.db."+entity);
		mongoTemplate.remove(new Query().addCriteria(Criteria.where("id").is(id)), entityClass);
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
	
	private List<Class> findDocuments(String basePackage) throws IOException, ClassNotFoundException
	{
	    ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
	    MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);

	    List<Class> candidates = new ArrayList<Class>();
	    String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
	                               resolveBasePackage(basePackage) + "/" + "**/*.class";
	    org.springframework.core.io.Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
	    for (org.springframework.core.io.Resource resource : resources) {
	        if (resource.isReadable()) {
	            MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
	            if (isCandidate(metadataReader)) {
	                candidates.add(Class.forName(metadataReader.getClassMetadata().getClassName()));
	            }
	        }
	    }
	    return candidates;
	}

	private String resolveBasePackage(String basePackage) {
	    return ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage));
	}

	private boolean isCandidate(MetadataReader metadataReader) throws ClassNotFoundException
	{
	    try {
	        Class c = Class.forName(metadataReader.getClassMetadata().getClassName());
	        if (c.getAnnotation(Document.class) != null) {
	            return true;
	        }
	    }
	    catch(Throwable e){
	    }
	    return false;
	}
	
}
