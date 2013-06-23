package edu.asu.krypton.service.redis.listeners;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import edu.asu.krypton.service.atmosphere.chat.BroadcastMessage;
import edu.asu.krypton.service.atmosphere.chat.ChatService;

public class ChatListener implements MessageListener {
	
	@Autowired(required=true)
	private ObjectMapper objectMapper;
	
	@Autowired(required=true)
	private ChatService chatService;
	
    @Override
    public void onMessage( final Message message, final byte[] pattern ) {
    	try {
			BroadcastMessage broadcastMessage = objectMapper.readValue(message.toString(), BroadcastMessage.class);
			chatService.broadcastMessage(broadcastMessage);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}