package edu.asu.krypton.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/membership/social")
public class SocialController extends edu.asu.krypton.controllers.Controller {
	
	private final String SOCIAL_VIEW = "social";
	
	@Override
	@RequestMapping(method=RequestMethod.GET)
	public String defaultView(ModelMap model){
		return defaultView(model,SOCIAL_VIEW);
	}
}
