package edu.asu.krypton.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value="customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
	private final static Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	@Autowired(required=true)
	private RegistrationService registrationService;
	
	
	/**
	 * Returns a populated {@link UserDetails} object.
	 * The username is first retrieved from the database and then mapped to 
	 * a {@link UserDetails} object.
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			logger.info("loadUserByUsername");
			edu.asu.krypton.model.persist.db.User exampleUser
						= new edu.asu.krypton.model.persist.db.User();
			exampleUser.setUsername(username);
			edu.asu.krypton.model.persist.db.User domainUser 
						= registrationService.findUserByUsername(exampleUser);
			if(domainUser == null) 
				throw new UsernameNotFoundException(
							   String.format("there is no user with username : \"%s\"", username)
					   );
			
			logger.info("user found");
			
			boolean enabled					= true;
			boolean accountNonExpired 	 	= true;
			boolean credentialsNonExpired   = true;
			boolean accountNonLocked 		= true;
			UserDetails user = new User(
					domainUser.getUsername(), 
					domainUser.getPassword(),
					enabled,
					accountNonExpired,
					credentialsNonExpired,
					accountNonLocked,
					getAuthorities(domainUser.getRole().intValue())
			);
			//getLoggedInUser().setUsername(user.getPassword());
			return user;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * Retrieves a collection of {@link GrantedAuthority} based on a numerical role
	 * @param role the numerical role
	 * @return a collection of {@link GrantedAuthority
	 */
	public Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
		return getGrantedAuthorities(getRoles(role));
	}
	
	/**
	 * Converts a numerical role to an equivalent list of roles
	 * @param role the numerical role
	 * @return list of roles as as a list of {@link String}
	 */
	public List<String> getRoles(Integer role) {
		List<String> roles = new ArrayList<String>();
		
		if (role.intValue() == 1) {
			roles.add("ROLE_USER");
			roles.add("ROLE_ADMIN");
		} else if (role.intValue() == 2) {
			roles.add("ROLE_USER");
		}
		return roles;
	}
	
	/**
	 * Wraps {@link String} roles to {@link SimpleGrantedAuthority} objects
	 * @param roles {@link String} of roles
	 * @return list of granted authorities
	 */
	public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String role : roles)
			authorities.add(new SimpleGrantedAuthority(role));
		return authorities;
	}

	public RegistrationService getRegistrationService() {
		return registrationService;
	}

	public void setRegistrationService(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}

//	public EhCacheBasedUserCache getUserCache() {
//		return userCache;
//	}
//
//	public void setUserCache(EhCacheBasedUserCache userCache) {
//		this.userCache = userCache;
//	}

}
