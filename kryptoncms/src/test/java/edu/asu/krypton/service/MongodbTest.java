package edu.asu.krypton.service;

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
import edu.asu.krypton.model.persist.db.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class MongodbTest {
	private Logger logger = org.slf4j.LoggerFactory.getLogger(MongodbTest.class);
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Test
	public void test() {

	}

}
