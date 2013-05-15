package edu.asu.krypton.service;

import java.math.BigInteger;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.krypton.exceptions.CustomRuntimeException;
import edu.asu.krypton.model.persist.db.User;

//@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class RegistrationServiceTest  {
	@Autowired(required=true)
	private RegistrationService registrationService;
	
	private final static Logger logger = LoggerFactory.getLogger(RegistrationServiceTest.class);

	private final int NO_OF_USERS = 50;
	private final int NO_OF_ADMINS = 50;
	@Test
	public void mainTest() throws CustomRuntimeException {
		final String USERNAME_PREFIX = "username#";
		final String ADMIN_PREFIX = "admin#";
		final String PASSWORD_PREFIX = "password#";
		User user = null;
//		Role role = null;
		for(int i=0;i<NO_OF_USERS;i++){
			user = new User();
			user.setUsername(USERNAME_PREFIX+i);
			user.setPassword(PASSWORD_PREFIX+i);
//			role = new Role();
//			role.setRole(2);
			user.setRole(BigInteger.valueOf(2));
//			role.setUser(user);
			logger.info("registering user #"+i);
			registrationService.registerUser(user);
		}
		for(int i=0;i<NO_OF_ADMINS;i++){
			user = new User();
			user.setUsername(ADMIN_PREFIX+i);
			user.setPassword(PASSWORD_PREFIX+i);
//			role = new Role();
//			role.setRole(1);
			user.setRole(BigInteger.valueOf(1));
//			role.setUser(user);
			logger.info("registering admin #"+i);
			registrationService.registerUser(user);
		}
//		for(int i=0;i<NO_OF_TRIALS;i++){
//			user = new User();
//			user.setUsername(USERNAME_PREFIX+i);
//			logger.info("deleting user #"+i);
//			user = registrationService.findUserByUsername(user);
//			registrationService.unregisterUser(user);
//		}
	}

	public RegistrationService getRegistrationService() {
		return registrationService;
	}

	public void setRegistrationService(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}

}
