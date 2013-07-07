package edu.asu.krypton.controllers.webservices;

import java.io.StringWriter;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.krypton.model.message_proxies.Message;
import edu.asu.krypton.model.message_proxies.QueryMessage;
import edu.asu.krypton.model.persist.db.DbEntity;
import edu.asu.krypton.model.repository.Repository;

@Controller
@RequestMapping(value="webservice",produces={MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
public class GenericDatabaseWebServiceController {

	//autowired at constructor
	private Jaxb2Marshaller jaxbMarshaller;
	
	@Autowired(required=true)
	private Repository<Object> repository;
	
	@Autowired(required=true)
	private ObjectMapper objectMapper;
	
	@Autowired(required=true)
	public GenericDatabaseWebServiceController(Jaxb2Marshaller jaxbMarshaller){
		this.jaxbMarshaller = jaxbMarshaller;
		Map<String, Boolean> marshallerProperties = new HashMap<String, Boolean>();
        marshallerProperties.put(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.setMarshallerProperties(marshallerProperties);
	}
	
//	public GenericDatabaseWebServiceController(){
//	}
	
	@RequestMapping(method=RequestMethod.GET,value="{entity}.{dataType}")
	public @ResponseBody String get(
			@PathVariable String entity,
			@PathVariable String dataType,
			@RequestParam(defaultValue="10") int pageSize,
			@RequestParam(defaultValue="1") int pageNo,
			@RequestParam(defaultValue="sensitive") String caseSensitivity,
			@RequestParam String appName,
			@RequestParam String signedAppName,
			HttpServletRequest request,
			HttpServletResponse response) {
		StringWriter st = new StringWriter();
		Class<?> entityClass;
		try {
			entityClass = Class.forName("edu.asu.krypton.model.persist.db."+entity);
			Query query = new Query();
	    	query.limit(pageSize);
	    	query.skip(pageSize*(pageNo-1));
			Map parms = request.getParameterMap();
			//dao.getSession().createQuery("")
			Enumeration enumeration = request.getParameterNames();
			while (enumeration.hasMoreElements()) {
				String parameterName = (String) enumeration.nextElement();
				if(parameterName.equals("pageSize") || parameterName.equals("pageNo") || parameterName.equals("caseSensitivity")) continue;
				query.addCriteria(Criteria.where(parameterName).regex(request.getParameter(parameterName),caseSensitivity.equals("sensitive")?"":"i"));
			}
			@SuppressWarnings("unchecked")
			Collection<Object> result = (Collection<Object>) repository.findByQuery(query, entityClass);
			
			QueryMessage<Object> queryMessage = new QueryMessage<Object>().setQueryResult(result);
			if(dataType.equalsIgnoreCase("xml"))
				jaxbMarshaller.marshal(queryMessage.setSuccessful(true) , new StreamResult(st));
			else if(dataType.equalsIgnoreCase("json"))
				objectMapper.writeValue(st, queryMessage.setSuccessful(true) );
			else throw new IllegalArgumentException("["+dataType+"]"+"is not a valid data type");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return st.toString();
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="{entity}.{dataType}")
	public @ResponseBody String delete(
			@PathVariable String entity,
			@PathVariable String dataType,
			@RequestParam(defaultValue="10") int pageSize,
			@RequestParam(defaultValue="1") int pageNo,
			@RequestParam(defaultValue="sensitive") String caseSensitivity,
			@RequestParam String appName,
			@RequestParam String signedAppName,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		StringWriter st = new StringWriter();
		Class<?> entityClass;
		try {
			entityClass = Class.forName("edu.asu.krypton.model.persist.db."+entity);
			Query query = new Query();
	    	query.limit(pageSize);
	    	query.skip(pageSize*(pageNo-1));
			Map parms = request.getParameterMap();
			//dao.getSession().createQuery("")
			StringBuilder buffer = new StringBuilder("from "+entity);
			Enumeration enumeration = request.getParameterNames();
			if(enumeration.hasMoreElements()) buffer.append(" where ");
			while (enumeration.hasMoreElements()) {
				String parameterName = (String) enumeration.nextElement();
				if(parameterName.equals("pageSize") || parameterName.equals("pageNo") || parameterName.equals("caseSensitivity")) continue;
				query.addCriteria(Criteria.where(parameterName).regex(request.getParameter(parameterName),caseSensitivity.equals("sensitive")?"":"i"));
			}
			repository.delete(query, entityClass);
			Message message = new Message();
			if(dataType.equalsIgnoreCase("xml"))
				jaxbMarshaller.marshal(message.setSuccessful(true) , new StreamResult(st));
			else if(dataType.equalsIgnoreCase("json"))
				objectMapper.writeValue(st, message.setSuccessful(true) );
			else throw new IllegalArgumentException("["+dataType+"]"+"is not a valid data type");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return st.toString();
	}
	
	@RequestMapping(method={RequestMethod.POST,RequestMethod.PUT},value="{entity}.{dataType}")
	public @ResponseBody String post(
			@PathVariable String entity,
			@PathVariable String dataType,
			@RequestBody String object,
			@RequestParam String appName,
			@RequestParam String signedAppName,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		StringWriter st = new StringWriter();
		Class<?> entityClass;
		try {
			entityClass = Class.forName("edu.asu.krypton.model.persist.db."+entity);
			Query query = new Query();
			repository.saveOrUpdate((DbEntity) objectMapper.readValue(object, entityClass));
			Message message = new Message();
			if(dataType.equalsIgnoreCase("xml"))
				jaxbMarshaller.marshal(message.setSuccessful(true) , new StreamResult(st));
			else if(dataType.equalsIgnoreCase("json"))
				objectMapper.writeValue(st, message.setSuccessful(true) );
			else throw new IllegalArgumentException("["+dataType+"]"+"is not a valid data type");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return st.toString();
	}
}
