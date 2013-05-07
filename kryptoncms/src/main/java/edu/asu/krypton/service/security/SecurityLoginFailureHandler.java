package edu.asu.krypton.service.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import edu.asu.krypton.controllers.MembershipController;

@Service
public class SecurityLoginFailureHandler extends
		SimpleUrlAuthenticationFailureHandler {
	@Autowired
	private MembershipController membershipController;
	//private final static Logger logger = LoggerFactory.getLogger(SecurityLoginFailureHandler.class);
	@Override
	public void onAuthenticationFailure(
			HttpServletRequest request,
			HttpServletResponse response,
			AuthenticationException exception)
			throws IOException, ServletException {
		membershipController.login(request, response,this,null,exception);
	}
	public MembershipController getMembershipController() {
		return membershipController;
	}

	public void setMembershipController(MembershipController membershipController) {
		this.membershipController = membershipController;
	}
	
}
