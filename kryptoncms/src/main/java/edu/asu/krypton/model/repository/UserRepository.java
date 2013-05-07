package edu.asu.krypton.model.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import edu.asu.krypton.model.persist.db.User;

@Repository
public class UserRepository extends edu.asu.krypton.model.repository.Repository<User> {
	public UserRepository() {
		setPersistentClass(User.class);
	}
	
	public List<String> getAllUsernames(){
		return getAllOf("username");
	}
	//private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);
	
}
