package edu.asu.krypton.service.atmosphere.chat;


import java.io.IOException;
import java.util.Collection;

import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResource.TRANSPORT;
import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.cpr.MetaBroadcaster;
import org.atmosphere.cpr.Meteor;
import org.bson.types.ObjectId;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import edu.asu.krypton.model.message_proxies.ChatMessage;
import edu.asu.krypton.model.persist.db.ChatConversation;
import edu.asu.krypton.model.persist.db.DbEntity;
import edu.asu.krypton.model.persist.db.User;
import edu.asu.krypton.model.repository.Repository;

@Service
public class ChatService {
	private final static Logger logger = LoggerFactory.getLogger(ChatService.class);
	
	@Autowired(required=true)
	private Repository<Object> repository;
	
	private long anonymousCount;

	public void handShake(edu.asu.krypton.model.persist.db.User user,final AtmosphereResource atmosphereResource) {
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

	public Collection<ChatConversation> getChatConversations(User user) {
		Query query = new Query();
		query.addCriteria(Criteria.where("parties.$id").is(new ObjectId(user.getId())));
		@SuppressWarnings("unchecked")
		Collection<ChatConversation> chatConversations = (Collection<ChatConversation>) repository.findByQuery(query, ChatConversation.class);
		return chatConversations;
	}
	
	public void saveOrUpdateConversation(ChatConversation chatConversation){
		repository.saveOrUpdate(chatConversation);
	}

}
