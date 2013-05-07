package edu.asu.krypton.service.atmosphere.chat;


import java.io.IOException;

import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResource.TRANSPORT;
import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.cpr.MetaBroadcaster;
import org.atmosphere.cpr.Meteor;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import edu.asu.krypton.model.message_proxies.ChatMessage;

@Service
public class ChatService {
	private final static Logger logger = LoggerFactory.getLogger(ChatService.class);
	
	private long anonymousCount;

	public void handShake(User user,final AtmosphereResource atmosphereResource) {
		Meteor m = Meteor.build(atmosphereResource.getRequest())
				 .addListener(
						 new AtmosphereResourceEventListenerAdapter()
		   );
		String path = String.format("/%s/%s",
				user!=null?user.getUsername():"Anonymous",
				user!=null?"":Long.toString(++anonymousCount));
		
		BroadcasterFactory broadcasterFactory = BroadcasterFactory.getDefault();
		Broadcaster broadcaster = null;
		try{
			broadcaster = broadcasterFactory.get(path);
			logger.debug("broadcaster created with the path "+path);
		}catch(IllegalStateException ex){
			broadcaster = broadcasterFactory.lookup(path);
			logger.debug("using the existing broadcaster "+path);
		}
		broadcaster.addAtmosphereResource(atmosphereResource);
		
		m.resumeOnBroadcast(
				m.transport() == TRANSPORT.LONG_POLLING
		).suspend(-1);
		
	}

	public void broadcast(ChatMessage chatMessage) throws JsonGenerationException, JsonMappingException, IOException {
		for(String destination : chatMessage.getDestinations()){
			String path = String.format("/%s/%s",
								destination,
								destination.equals("Anonymous")?"*":""
							);
			MetaBroadcaster.getDefault().broadcastTo(path,new ObjectMapper().writeValueAsString(chatMessage));
			logger.debug(String.format("broadcasting \"%s\" to the following path \"%s\"",chatMessage.getBody(),path));
		}
	}
	
	

}
