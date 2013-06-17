package edu.asu.krypton.service.redis.listeners;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import edu.asu.krypton.model.message_proxies.MenuMessage;
import edu.asu.krypton.service.MenuService;

public class MenuListener implements MessageListener {
	
//	@Autowired(required=true)
//	private ObjectMapper objectMapper;
	
	@Autowired(required=true)
	private MenuService menuService;
	
    @Override
    public void onMessage( final Message message, final byte[] pattern ) {
    	try {
    		MenuMessage menuMessage = new ObjectMapper().readValue(message.toString(),MenuMessage.class);
    		menuService.broadcast(menuMessage);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}