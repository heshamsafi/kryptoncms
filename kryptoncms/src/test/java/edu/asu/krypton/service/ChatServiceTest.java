package edu.asu.krypton.service;

import java.util.ArrayList;
import java.util.Collection;
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

import edu.asu.krypton.model.persist.db.ChatConversation;
import edu.asu.krypton.model.persist.db.DbEntity;
import edu.asu.krypton.model.persist.db.User;
import edu.asu.krypton.model.repository.Repository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ChatServiceTest {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(ChatServiceTest.class);
	@Autowired
	private Repository<Object> repository;
	
	@Autowired
	private RegistrationService registrationService;
	
	@Test
	public void test() {
		for (User user : registrationService.getAllUsers(false)) {
			List<User> users = new ArrayList<User>();
			ChatConversation chatConversation = new ChatConversation();
			users.add(user);
			chatConversation.setParties(users);
			repository.saveOrUpdate(chatConversation);
		}
		

	}

}
