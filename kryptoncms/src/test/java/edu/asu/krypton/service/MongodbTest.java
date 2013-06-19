package edu.asu.krypton.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.asu.krypton.model.persist.db.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class MongodbTest {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(MongodbTest.class);
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Test
	public void test() {
		Query query = new Query();
		query.addCriteria(Criteria.where("role").regex(".*","i"));
		List<User> list = mongoTemplate.find(query, User.class);
		for(User user:list)
			System.out.println(user.toString());
	}

}
