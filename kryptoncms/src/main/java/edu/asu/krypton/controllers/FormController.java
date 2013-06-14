 package edu.asu.krypton.controllers;

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

import edu.asu.krypton.model.persist.db.Article;

@Controller
@RequestMapping(value="/form")
public class FormController {
	private static final Logger logger = LoggerFactory.getLogger(FormController.class);
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@RequestMapping(value = "/{fullClassName}/{id}", method = RequestMethod.GET)
	public String getFormById(@RequestBody String json,Model model,@PathVariable String fullClassName,@PathVariable String id) throws Exception{
    	Query query = new Query();
    	query.addCriteria(Criteria.where("id").is(id));
    	Class<?> ownerTypeClass = Class.forName("edu.asu.krypton.model.persist.db."+fullClassName);
    	Object formObject = mongoTemplate.findOne(query, ownerTypeClass);
		return renderForm(model,formObject,"test");
	}

	@RequestMapping(value = "/{fullClassName}", method = RequestMethod.GET)
	public String getForm(@RequestBody String json,Model model,@PathVariable String fullClassName) throws Exception{
    	Class<?> ownerTypeClass = Class.forName("edu.asu.krypton.model.persist.db."+fullClassName);
		return renderForm(model,ownerTypeClass.newInstance(),"test");
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String test(Model model) throws Exception{
		Object formObject = new Article();
		((Article)formObject).setContent("test");
		((Article)formObject).setTitle("title");
		((Article)formObject).setId("sadea3e");
		return renderForm(model,formObject,"test");
	}
	
	public String renderForm(Model model,Object formObject,String submitURL) throws Exception {
		model.addAttribute("formObject", formObject);
		model.addAttribute("URLTosubmit", submitURL);
		return "form";
	}
}