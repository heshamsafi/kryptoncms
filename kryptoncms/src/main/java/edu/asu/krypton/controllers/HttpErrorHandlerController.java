package edu.asu.krypton.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(method=RequestMethod.GET,value="error")
public class HttpErrorHandlerController extends edu.asu.krypton.controllers.Controller {
	
	private final String DEFAULT_BODIES_DIR = "bodies/";
	private final String ERROR_PAGE = "error";
	
	@RequestMapping(value="{errorCode}")
	public String notFound(HttpServletRequest request,Model model,@PathVariable String errorCode){
		model.addAttribute("errorCode", errorCode);
		return appropriateView(request, DEFAULT_BODIES_DIR+ERROR_PAGE, defaultView(model, ERROR_PAGE));
	}
}
