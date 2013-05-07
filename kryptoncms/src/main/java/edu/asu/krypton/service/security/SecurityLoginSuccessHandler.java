package edu.asu.krypton.service.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import edu.asu.krypton.controllers.MembershipController;

@Service
public class SecurityLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	@Autowired
	private MembershipController membershipController;
	//private static final Logger logger = LoggerFactory.getLogger(SecurityLoginSuccessHandler.class);
	
	public SecurityLoginSuccessHandler() {
		super();
	}
 
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {
		membershipController.login(request, response,this,auth,null);
	}

	public MembershipController getMembershipController() {
		return membershipController;
	}

	public void setMembershipController(MembershipController membershipController) {
		this.membershipController = membershipController;
	}
	
}
