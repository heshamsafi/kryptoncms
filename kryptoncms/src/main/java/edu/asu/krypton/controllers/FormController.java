 package edu.asu.krypton.controllers;

import java.io.IOException;

import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResource.TRANSPORT;
import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.cpr.Meteor;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.krypton.model.message_proxies.Message;
import edu.asu.krypton.model.message_proxies.ScaffoldMessage;
import edu.asu.krypton.service.ScaffoldService;
import edu.asu.krypton.service.redis.Publisher;

@Controller
@RequestMapping(value="/form")
public class FormController {
	private static final Logger logger = LoggerFactory.getLogger(FormController.class);
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired(required=true)
	private ObjectMapper objectMapper;
	
	@Autowired(required=true)
	private ScaffoldService scaffoldService;
	
	@RequestMapping(value="echo",method = RequestMethod.GET)
	@ResponseBody
	public void handshake(final AtmosphereResource atmosphereResource,@RequestParam(defaultValue="") String j_username) throws IOException {
		Meteor m = Meteor.build(atmosphereResource.getRequest()).addListener(new AtmosphereResourceEventListenerAdapter());
		String path = String.format("/form/echo");

		BroadcasterFactory broadcasterFactory = BroadcasterFactory.getDefault();
		Broadcaster broadcaster = null;
		try {
			broadcaster = broadcasterFactory.get(path);
		} catch (IllegalStateException ex) {
			broadcaster = broadcasterFactory.lookup(path);
		}
		broadcaster.addAtmosphereResource(atmosphereResource);
		m.resumeOnBroadcast(m.transport() == TRANSPORT.LONG_POLLING).suspend(-1);
	}

	@RequestMapping(value="echo",method = RequestMethod.POST)
	@ResponseBody
	public void broadcast(@RequestBody String requestBody,AtmosphereResource atmosphereResource) throws IOException, ClassNotFoundException {
		if (requestBody.equals("closing")) {
			atmosphereResource.resume();
			return;
		}
		ScaffoldMessage scaffoldMessage = objectMapper.readValue(requestBody,ScaffoldMessage.class);
		scaffoldService.serviceScaffoldCommand(scaffoldMessage);
	}
	
	@RequestMapping(value={"/{fullClassName}","/{fullClassName}/{id}"},method=RequestMethod.POST)
	public @ResponseBody Message submitCreateForm(@RequestBody String requestBody,@PathVariable String fullClassName) throws JsonParseException, JsonMappingException, IOException, ClassNotFoundException{
		Class<?> ownerTypeClass = Class.forName(fullClassName);
		mongoTemplate.save(objectMapper.readValue(requestBody, ownerTypeClass));
		return new Message().setSuccessful(true);
	}
	
	/**
	 * edit an existing form
	 * @param json
	 * @param model
	 * @param fullClassName
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{fullClassName}/{id}", method = RequestMethod.GET)
	public String getFormById(@RequestBody String json,Model model,@PathVariable String fullClassName,@PathVariable String id) throws Exception{
    	Query query = new Query();
    	query.addCriteria(Criteria.where("id").is(id));
    	Class<?> ownerTypeClass = Class.forName(fullClassName);
    	Object formObject = mongoTemplate.findOne(query, ownerTypeClass);
		return renderForm(model,formObject,fullClassName);
	}

	/**
	 * create new entity form
	 * @param json
	 * @param model
	 * @param fullClassName
	 * @return view
	 * @throws Exception
	 * 
	 */
	@RequestMapping(value = "/{fullClassName}", method = RequestMethod.GET)
	public String getForm(@RequestBody String json,Model model,@PathVariable String fullClassName) throws Exception{
    	Class<?> ownerTypeClass = Class.forName(fullClassName);
		return renderForm(model,ownerTypeClass.newInstance(),fullClassName);
	}
	

	public String renderForm(Model model,Object formObject,String submitURL) throws Exception {
		model.addAttribute("formObject", formObject);
		model.addAttribute("URLTosubmit", submitURL);
		return "form";
	}
}