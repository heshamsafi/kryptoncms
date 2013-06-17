package edu.asu.krypton.service.redis.listeners;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import edu.asu.krypton.model.message_proxies.ChatMessage;
import edu.asu.krypton.service.atmosphere.chat.ChatService;

public class ChatListener implements MessageListener {
	
	@Autowired(required=true)
	private ObjectMapper objectMapper;
	
	@Autowired(required=true)
	private ChatService chatService;
	
    @Override
    public void onMessage( final Message message, final byte[] pattern ) {
    	try {
			ChatMessage chatMessage = objectMapper.readValue(message.toString(), ChatMessage.class);
			System.out.println(chatMessage);
			chatService.broadcast(chatMessage);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}