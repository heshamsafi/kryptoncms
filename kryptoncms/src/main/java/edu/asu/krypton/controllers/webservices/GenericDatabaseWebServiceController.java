package edu.asu.krypton.controllers.webservices;

import java.io.StringWriter;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.krypton.model.message_proxies.QueryMessage;

@Controller
@RequestMapping(value="webservice")
public class GenericDatabaseWebServiceController {

//	@Autowired(required=true)
//	private DataAccessObject<?> dao;
	 
	@Autowired(required=true)
	private Jaxb2Marshaller jaxbMarshaller;
	
	@Autowired(required=true)
	private MongoTemplate mongoTemplate;
	
	@RequestMapping(method=RequestMethod.GET,value="{entity}.{dataType}",produces={MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody String selectAllOf(
			@PathVariable String entity,
			@PathVariable String dataType,
			@RequestParam(defaultValue="10") int pageSize,
			@RequestParam(defaultValue="1") int pageNo,
			HttpServletRequest request) {
		Map<String, Boolean> marshallerProperties = new HashMap<String, Boolean>();
        marshallerProperties.put(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.setMarshallerProperties(marshallerProperties);
		
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
				if(parameterName.equals("pageSize") || parameterName.equals("pageNo")) continue;
				query.addCriteria(Criteria.where(parameterName).regex(request.getParameter(parameterName),"i"));
			}
			if(dataType.equalsIgnoreCase("xml"))
				jaxbMarshaller.marshal(
					new QueryMessage<Object>().setQueryResult((Collection<Object>) mongoTemplate.find(query,entityClass)).setSuccessful(true) 
					//criteria.list()
					, new StreamResult(st));
			else if(dataType.equalsIgnoreCase("json"))
				new ObjectMapper().writeValue(st, new QueryMessage<Object>().setQueryResult((Collection<Object>) mongoTemplate.find(query,entityClass)).setSuccessful(true) );
			else throw new IllegalArgumentException("["+dataType+"]"+"is not a valid data type");
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return st.toString();
	}
}
