package edu.asu.krypton.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.krypton.exceptions.CustomRuntimeException;
import edu.asu.krypton.model.message_proxies.MembershipStatus;
import edu.asu.krypton.model.message_proxies.Message;
import edu.asu.krypton.model.message_proxies.QueryMessage;
import edu.asu.krypton.model.persist.db.Role;
import edu.asu.krypton.model.persist.db.User;
import edu.asu.krypton.service.RegistrationService;

@Controller
@RequestMapping(value="/membership")
public class MembershipController extends edu.asu.krypton.controllers.Controller {
	
	@Autowired
	private RegistrationService registrationService;
	
	private final String LOGIN_VIEW	   = "login";
	private final String REGISTER_VIEW = "register";
	
	private final String BODIES_DIR = "bodies/";
	
	private static final Logger logger = LoggerFactory.getLogger(MembershipController.class);

	
	@RequestMapping(method=RequestMethod.GET,value="/login")
	public String loginView(ModelMap model,HttpServletRequest request){
		return appropriateView(request, BODIES_DIR+LOGIN_VIEW, defaultView(model,LOGIN_VIEW));
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/status")
	public @ResponseBody Message membershipStatus(){
		MembershipStatus membership = new MembershipStatus();
		try{
			User loggedInUser = registrationService.getLoggedInDbUser();
			if (loggedInUser == null) return membership.setAdmin(false).setLoggedin(false).setSuccessful(true);
			return membership.setAdmin(registrationService.isUserAdmin(loggedInUser)).setLoggedin(true).setSuccessful(true);
		}catch (Exception e) {
			return membership.setSuccessful(false).setErrorMessage(e.getMessage());
		}
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/register")
	public String registerView(ModelMap model,HttpServletRequest request){
		return appropriateView(request, BODIES_DIR+REGISTER_VIEW, defaultView(model, REGISTER_VIEW));
	}
	
	public void login(
			HttpServletRequest request,
			HttpServletResponse response,
			Object securityHandler,
			Authentication auth,
			AuthenticationException authEx) throws IOException, ServletException {

		boolean status = securityHandler instanceof SimpleUrlAuthenticationSuccessHandler;
		logger.info(String.format("user authenticated %ssuccessfully",(status?"":"un")));
		
		if (isAjax(request)) {
			response.getWriter().print(
				new ObjectMapper().writeValueAsString(
					new Message().setSuccessful(status).setErrorMessage("incorrect credentials")
				)
			);
		} else {//sync request
			if(status == true)
				((SimpleUrlAuthenticationSuccessHandler)securityHandler)
				.onAuthenticationSuccess(request, response, auth);
			else
				((SimpleUrlAuthenticationFailureHandler)securityHandler)
				.onAuthenticationFailure(request, response, authEx);
		}
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/register/{roleRank}",consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Message register(@RequestBody User user,@PathVariable int roleRank){
			logger.info("username = "+user.getUsername());
			logger.info("password = "+user.getPassword());
			//logger.info("rememberMe = "+Boolean.toString(user.isRememberMe()));
			Role role = new Role();
			role.setRole(roleRank);
			role.setUser(user);
			user.setRole(role);
			try{
				return new Message().setSuccessful(
						getRegistrationService().registerUser(user,role)
					);
			}catch (CustomRuntimeException ex) {
				return new Message().setSuccessful(false).setErrorMessage(ex.getMessage());
			}
	}
	@RequestMapping(method=RequestMethod.GET,value="/logout/success")
	public @ResponseBody Message logout(){
		return new Message().setSuccessful(true);
	}
	
	public RegistrationService getRegistrationService() {
		return registrationService;
	}
	public void setRegistrationService(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}
}
