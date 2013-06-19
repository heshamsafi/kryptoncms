package edu.asu.krypton.service.redis.listeners;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import edu.asu.krypton.model.message_proxies.OutBoundCommentProxy;
import edu.asu.krypton.service.CommentService;

public class CommentListener implements MessageListener {
	
	@Autowired(required=true)
	private ObjectMapper objectMapper;
	
	@Autowired(required=true)
	private CommentService commentService;
	
    @Override
    public void onMessage( final Message message, final byte[] pattern ) {
    	try {
    		@SuppressWarnings("unchecked")
    		OutBoundCommentProxy proxy = objectMapper.readValue(message.toString(), OutBoundCommentProxy.class);
			commentService.broadcastCommment(proxy);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}