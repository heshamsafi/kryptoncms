package edu.asu.krypton.controllers;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.NativeWebRequest;

public abstract class Controller {
	protected final String TEMPLATE_NAME = "template";
	private final static Logger logger = org.slf4j.LoggerFactory.getLogger(Controller.class);
	protected void addViewToModelMap(ModelMap model,String viewName){
		model.addAttribute("view_name",viewName);
	}
	protected void addViewToModelMap(Model model,String viewName){
		model.addAttribute("view_name",viewName);
	}
	
	protected String defaultView(ModelMap model,String viewName){
		addViewToModelMap(model, viewName);
		return TEMPLATE_NAME;
	}
	protected String defaultView(Model model,String viewName){
		logger.debug("attached this view body to template model : "+ viewName);
		addViewToModelMap(model, viewName);
		return TEMPLATE_NAME;
	}
	
	public String defaultView(ModelMap model){return null;}
	
	protected boolean isAjax(HttpServletRequest request){
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}
	
	protected boolean isAjax(NativeWebRequest request){
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}
	
	protected String appropriateView(HttpServletRequest request,String ajaxView,String syncView){
		String view = isAjax(request)?ajaxView:syncView;
		logger.debug("looking for this view : "+view);
		return view;
	}
	
	protected String appropriateView(NativeWebRequest request,String ajaxView,String syncView){
		return isAjax(request)?ajaxView:syncView;
	}
	
}
