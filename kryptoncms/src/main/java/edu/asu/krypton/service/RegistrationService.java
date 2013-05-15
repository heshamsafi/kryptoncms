package edu.asu.krypton.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import edu.asu.krypton.exceptions.CustomRuntimeException;
import edu.asu.krypton.model.persist.db.User;
import edu.asu.krypton.model.repository.UserRepository;

@Service
public class RegistrationService {
	@Autowired(required=true)
	private UserRepository userRepository;
	
	private final static Logger logger = LoggerFactory.getLogger(RegistrationService.class);
	
	@SessionDependant
	public void unregisterUser(User user){
		userRepository.delete(user);
	}
	@SessionDependant
	public void registerUser(User user) throws CustomRuntimeException{
		if(userRepository.findByUsername(user.getUsername()) == null)
			userRepository.saveOrUpdate(user);
		else throw new CustomRuntimeException("This username is taken");
	}
	@SessionDependant
	public User findUserByUsername(User exampleUser){
		User domainUser = null;
		try{
			domainUser = //(User)userRepository.findByExample(exampleUser).get(0);
						userRepository.findByUsername(exampleUser.getUsername());
			logger.debug("db user" + domainUser.getUsername());
		} catch(NullPointerException ex){
		} catch(IndexOutOfBoundsException ex){
		} catch(ClassCastException ex){}
		return domainUser;
	}
	//dangerous
	//check call hierarchy to make sure it is not called in deployment
	@SessionDependant
	public void flushUsers(){
		userRepository.deleteAll();
	}
	
	@SessionDependant
	public List<User> getAllUsers(boolean excludeSelf){
		List<User> allUsers = userRepository.getAll();
		if(excludeSelf){
			User example = new User();
			example.setUsername(getLoggedInUser().getUsername());
			allUsers.remove(allUsers.indexOf(example));
		}
		return allUsers;
	}
	
	@SessionDependant
	public List<String> getAllUsernames(boolean excludeSelf){
		List<String> allUsernames = userRepository.getAllUsernames();
		if(excludeSelf) allUsernames.remove(allUsernames.indexOf(getLoggedInUser().getUsername()));
		return allUsernames;
	}
	
	@SessionDependant
	public boolean isUserAdmin(User user){
		try{
			logger.debug(user.getUsername());
			user = 
					//userRepository.findByExample(user).get(0);
					userRepository.findById(user.getId());
			
			return user.getRole().intValue() == 1;
		}catch(NullPointerException ex){
			return false;
		}
	}
	
	/**
	 * Must be called from within the context of a populated session (there must be a valid JSESSIONID
	 * otherwise it will not be able to get loggedInUser and will return null in that case
	 * @return LoggedInUser or null
	 */
	public org.springframework.security.core.userdetails.User getLoggedInUser(){
		try{
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			Object 		   principal = authentication.getPrincipal();
			logger.debug(principal.toString()); 
			return (principal instanceof org.springframework.security.core.userdetails.User)?
				   ((org.springframework.security.core.userdetails.User)principal):
					null;
		} catch(NullPointerException ex){
			return null;
		}
	}
	@SessionDependant
	public User getLoggedInDbUser(){
		try{
			UserDetails user = getLoggedInUser();
			User dbUser = new User();
			dbUser.setUsername(user.getUsername());
		return findUserByUsername(dbUser);
		}catch(NullPointerException ex){
			return null;
		}
	}
	public UserRepository getUserRepository() {
		return userRepository;
	}
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

}
