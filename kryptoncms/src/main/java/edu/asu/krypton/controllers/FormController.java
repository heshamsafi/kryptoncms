package edu.asu.krypton.controllers;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/form")
public class FormController {
	
	@RequestMapping(value = "/{className}", method = RequestMethod.GET)
	public String renderForm(Model model, @RequestBody @PathVariable String className) throws Exception {
		Map<String, Object> attributes = model.asMap();
		Object formObject = attributes.get("targetObject");
		String submitURL = (String)attributes.get("submitURL");
//		Object formObject = new Article();
//		((Article)formObject).setId(new Long(20));
//		((Article)formObject).setName("article10name");
//		((Article)formObject).setTitle("article10Title");
//		((Article)formObject).setObsolete(false);
//		((Article)formObject).setContent("<p>Article10 test <a href=\"http://localhost:8080/kryptoncms/\">Krypton</a></p>");
//		((Article)formObject).setDescription("aricle10Desc.");
		model.addAttribute("formObject", formObject);
		model.addAttribute("URLTosubmit", submitURL);
		return "form";
	}
}