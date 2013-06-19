package edu.asu.krypton.model.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import edu.asu.krypton.model.persist.db.User;

@Repository
public class UserRepository extends edu.asu.krypton.model.repository.Repository<User> {
	public UserRepository() {
		setPersistentClass(User.class);
	}
	
	public List<String> getAllUsernames(){
		List<String> usernames = new ArrayList<String>();
		for(User user : getAll()){
			usernames.add(user.getUsername());
		}
		return usernames;
	}
	//private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

	public User findByUsername(String username) {
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(username));
		return mongoTemplate.findOne(query, getPersistentClass());
	}
	
}
