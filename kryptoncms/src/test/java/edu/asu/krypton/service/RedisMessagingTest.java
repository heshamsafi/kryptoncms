package edu.asu.krypton.service;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.asu.krypton.model.message_proxies.ChatMessage;
import edu.asu.krypton.service.redis.Publisher;


/**
 * @author hesham
 * this is great for prefilling the database (Y) aswell as testing
 */
//@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class RedisMessagingTest{
	
	@Autowired(required=true)
	private ObjectMapper objectMapper;
	
	@Autowired(required=true)
	private Publisher publisher;

	@Test
	public void test() throws JsonGenerationException, JsonMappingException, IOException {

	}
}
