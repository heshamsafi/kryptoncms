package edu.asu.krypton.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class HomeController extends edu.asu.krypton.controllers.Controller implements ApplicationContextAware {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private final String DEFAULT_VIEW = "home";
	private final String DEFAULT_BODY_DIR = "bodies/";
	private ApplicationContext applicationContext;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String defaultView(ModelMap model,HttpServletRequest request) throws IOException {
		model.addAttribute("profiles", applicationContext.getEnvironment().getActiveProfiles());
		logger.debug(applicationContext.getEnvironment().getActiveProfiles().toString());
		String path = request.getSession().getServletContext().getRealPath("/resources/css/bootstrcap.css");
		logger.debug(path);
		return appropriateView(request, DEFAULT_BODY_DIR+DEFAULT_VIEW, defaultView(model, "home"));
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
	
}