package edu.asu.krypton.model.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.krypton.exceptions.CustomRuntimeException;
import edu.asu.krypton.model.persist.db.Role;
import edu.asu.krypton.model.persist.db.User;


@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class UserRepositoryTest {
	@Autowired(required=true)
	private UserRepository userRepository;
	@Autowired(required=true)
	private RoleRepository roleRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(UserRepositoryTest.class);

	@Test
	public void register1000Users() throws CustomRuntimeException{
		User user;
		Role role;
		userRepository.getDao().openSession();
		for(int i=1;i<=1000;i++){
			logger.info("inserting user #"+i);
			user = new User();
			role = new Role();
			role.setRole(1);
			user.setUsername("user "+i);
			user.setPassword(String.format("user %d password", i));
			user.setRole(role);
			role.setUser(user);
			userRepository.saveOrUpdate(user);
			roleRepository.saveOrUpdate(role);
		}
		userRepository.getDao().killSession(true);
	}
	
//	@SuppressWarnings("unchecked")
//	@Test
//	public void deleteAllUsers(){
//		userRepository.getDao().openSession();
//		Criteria criteria = userRepository.getDao().getSession().createCriteria(User.class);
//		for(User user : (List<User>)criteria.list()){
//			logger.info("deleting user #"+user.getId());
//			userRepository.getDao().getSession().delete(user);
//			//cascades automatically
//			//userRepository.getDao().getSession().delete(user.getRole());
//		}
//		userRepository.getDao().killSession(true);
//	}


	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
}
