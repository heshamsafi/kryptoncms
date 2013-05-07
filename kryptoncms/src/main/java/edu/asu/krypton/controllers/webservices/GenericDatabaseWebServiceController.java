package edu.asu.krypton.controllers.webservices;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;

import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.krypton.model.message_proxies.Message;
import edu.asu.krypton.model.message_proxies.QueryMessage;
import edu.asu.krypton.model.repository.DataAccessObject;
import edu.asu.krypton.service.SessionDependant;

@Controller
@RequestMapping(value="webservice")
public class GenericDatabaseWebServiceController {

	@Autowired(required=true)
	private DataAccessObject<?> dao;
	 
	@Autowired(required=true)
	private Jaxb2Marshaller jaxbMarshaller;
	
	@RequestMapping(method=RequestMethod.GET,value="{entity}.{dataType}",produces={MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	@SessionDependant
	public @ResponseBody String selectAllOf(
			@PathVariable String entity,
			@PathVariable String dataType,
			HttpServletRequest request) throws XmlMappingException, HibernateException, IOException{
		Map<String, Boolean> marshallerProperties = new HashMap<String, Boolean>();
        marshallerProperties.put(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.setMarshallerProperties(marshallerProperties);
		
		StringWriter st = new StringWriter();
		Class<?> entityClass;
		try {
			entityClass = Class.forName("edu.asu.krypton.model.persist.db."+entity);
			Criteria criteria = dao.getSession().createCriteria(entityClass);
			Map parms = request.getParameterMap();
			//dao.getSession().createQuery("")
			StringBuilder buffer = new StringBuilder("from "+entity);
			Enumeration enumeration = request.getParameterNames();
			if(enumeration.hasMoreElements()) buffer.append(" where ");
			while (enumeration.hasMoreElements()) {
				String parameterName = (String) enumeration.nextElement();
				System.out.println("Parameter = " + parameterName);
				buffer.append(parameterName)
					  .append("=")
					  .append("'"+request.getParameter(parameterName)+"'");
				if(enumeration.hasMoreElements()) buffer.append(" AND ");
				criteria.add(Restrictions.eq(parameterName, request.getParameter(parameterName)));
			}
			if(dataType.equalsIgnoreCase("xml"))
				jaxbMarshaller.marshal(
					new QueryMessage<Object>().setQueryResult(dao.getSession().createQuery(buffer.toString()).list()).setSuccessful(true)
					//criteria.list()
					, new StreamResult(st));
			else if(dataType.equalsIgnoreCase("json"))
				new ObjectMapper().writeValue(st, new QueryMessage<Object>().setQueryResult(criteria.list()).setSuccessful(true));
			else throw new IllegalArgumentException("["+dataType+"]"+"is not a valid data type");
		} catch (ClassNotFoundException e) {
			if(dataType.equalsIgnoreCase("xml"))
				jaxbMarshaller.marshal(new Message().setSuccessful(false).setErrorMessage("["+entity+"] "+"is not a real entity"), new StreamResult(st));
			else if(dataType.equalsIgnoreCase("json"))
				new ObjectMapper().writeValue(st, new Message().setSuccessful(false).setErrorMessage("["+entity+"] "+"is not a real entity"));
		} catch (IllegalArgumentException e) {
			new ObjectMapper().writeValue(st, new Message().setSuccessful(false).setErrorMessage(e.getMessage()));
		}
		
		return st.toString();
	}
}
