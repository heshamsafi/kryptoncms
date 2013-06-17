package edu.asu.krypton.service.redis.listeners;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import edu.asu.krypton.model.message_proxies.ScaffoldMessage;
import edu.asu.krypton.service.ScaffoldService;

public class ScaffoldListener implements MessageListener {
	
	@Autowired(required=true)
	private ObjectMapper objectMapper;
	
	@Autowired(required=true)
	private ScaffoldService scaffoldService;
	
    @Override
    public void onMessage( final Message message, final byte[] pattern ) {
    	try {
    		ScaffoldMessage scaffoldMessage = objectMapper.readValue(message.toString(),ScaffoldMessage.class);
    		scaffoldService.serviceScaffoldCommand(scaffoldMessage);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }
}