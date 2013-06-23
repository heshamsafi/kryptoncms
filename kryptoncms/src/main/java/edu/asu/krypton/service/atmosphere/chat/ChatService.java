package edu.asu.krypton.service.atmosphere.chat;


import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

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
import edu.asu.krypton.model.persist.db.User;
import edu.asu.krypton.model.repository.Repository;
import edu.asu.krypton.service.RegistrationService;
import edu.asu.krypton.service.redis.Publisher;

@Service
public class ChatService {
	private final static Logger logger = LoggerFactory.getLogger(ChatService.class);
	
	@Autowired(required=true)
	private Repository<Object> repository;
	
	private long anonymousCount;
	
	@Autowired(required=true)
	private ObjectMapper objectMapper;
	
	@Autowired(required=true)
	private RegistrationService registrationService;
	
	@Autowired(required=true)
	private Publisher publisher;

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

	public void serveCommand(ChatMessage chatMessage) throws JsonGenerationException, JsonMappingException, IOException {
		BroadcastMessage broadcastMessage = new BroadcastMessage();
		if(chatMessage.getAction().equals("BROADCAST_MESSAGE")){
			Iterator<User> usersIterator = getConversationDestinations(chatMessage.getConversationId()).iterator();
			while(usersIterator.hasNext()){
				User user = usersIterator.next();
				String path = String.format("/%s/%s",
									user.getUsername(),
									user.getUsername().equals("Anonymous")?"*":""
								);
				broadcastMessage.getPaths().add(path);
				logger.debug(String.format("broadcasting \"%s\" to the following path \"%s\"",chatMessage.getMessageBody(),path));
			}
			broadcastMessage.setMessage(objectMapper.writeValueAsString(chatMessage));
			ChatConversation chatConversation = (ChatConversation) repository.findById(chatMessage.getConversationId(), ChatConversation.class);
			edu.asu.krypton.model.persist.db.ChatMessage dbChatMessage = new edu.asu.krypton.model.persist.db.ChatMessage();
			dbChatMessage.setMessage(chatMessage.getMessageBody());
			dbChatMessage.setSource(registrationService.findUserByUsername(chatMessage.getSourceUsername()));
			chatConversation.getMessages().add(dbChatMessage);
			repository.saveOrUpdate(dbChatMessage);
			repository.saveOrUpdate(chatConversation);
		}else if(chatMessage.getAction().equals("DELETE_CHANNEL")) {
			ChatConversation chatConversation = (ChatConversation) repository.findById(chatMessage.getConversationId(), ChatConversation.class);
			repository.deleteById(chatMessage.getConversationId(),ChatConversation.class);
			Iterator<User> iterator = chatConversation.getParties().iterator();
			while(iterator.hasNext()){
				User user = iterator.next();
				String path = String.format("/%s/%s",
						user.getUsername(),
						user.getUsername().equals("Anonymous")?"*":""
				);
				broadcastMessage.getPaths().add(path);
				broadcastMessage.setMessage(objectMapper.writeValueAsString(chatMessage));
			}
		}else if(chatMessage.getAction().equals("ADD_PARTY")){
			ChatConversation chatConversation = (ChatConversation) repository.findById(chatMessage.getConversationId(), ChatConversation.class);
			for (String newParty : chatMessage.getParties()) {
				chatConversation.getParties().add(registrationService.findUserByUsername(newParty));
			}
			repository.saveOrUpdate(chatConversation);
			Iterator<User> iterator = chatConversation.getParties().iterator();
			while(iterator.hasNext()){
				User user = iterator.next();
				String path = String.format("/%s/%s",
						user.getUsername(),
						user.getUsername().equals("Anonymous")?"*":""
				);
				broadcastMessage.getPaths().add(path);
				broadcastMessage.setMessage(objectMapper.writeValueAsString(chatMessage));
			}
			broadcastMessage.setMessage(objectMapper.writeValueAsString(chatMessage));
		}else if (chatMessage.getAction().equals("REMOVE_PARTY")){
			ChatConversation chatConversation = (ChatConversation) repository.findById(chatMessage.getConversationId(), ChatConversation.class);
			Iterator<User> iterator = chatConversation.getParties().iterator();
			while(iterator.hasNext()){
				User user = iterator.next();
				String path = String.format("/%s/%s",
						user.getUsername(),
						user.getUsername().equals("Anonymous")?"*":""
				);
				broadcastMessage.getPaths().add(path);
				broadcastMessage.setMessage(objectMapper.writeValueAsString(chatMessage));
				for (String deletedParty : chatMessage.getParties()) {
					if(user.getUsername().equals(deletedParty)) iterator.remove();
				}
			}
			repository.saveOrUpdate(chatConversation);
			broadcastMessage.setMessage(objectMapper.writeValueAsString(chatMessage));
		}else if(chatMessage.getAction().equals("ADD_CHANNEL")){
			try{
				ChatConversation chatConversation = new ChatConversation();
				User user = registrationService.findUserByUsername(chatMessage.getSourceUsername());
				edu.asu.krypton.model.persist.db.ChatMessage dbChatMessage= new edu.asu.krypton.model.persist.db.ChatMessage();
				dbChatMessage.setMessage(" Created This Conversation");
				dbChatMessage.setSource(user);
				repository.saveOrUpdate(dbChatMessage);
				dbChatMessage.getSource().setPassword(null);
				dbChatMessage.getSource().setRole(null);
				chatConversation.getMessages().add(dbChatMessage);
				chatConversation.getParties().add(user);
				saveOrUpdateConversation(chatConversation);
				logger.debug(chatConversation.toString());
				String path = String.format("/%s/%s",
						user.getUsername(),
						user.getUsername().equals("Anonymous")?"*":""
				);
				chatMessage.setConversationId(chatConversation.getId());
				chatMessage.setMessageBody(objectMapper.writeValueAsString(chatConversation.getMessages()));

				chatMessage.setParties(new String[chatConversation.getParties().size()]);
				Iterator<User> iterator = chatConversation.getParties().iterator();
				int i = 0;
				while(iterator.hasNext()){
					User party = iterator.next();
					chatMessage.getParties()[i++] = party.getUsername();
				}
				broadcastMessage.getPaths().add(path);
				broadcastMessage.setMessage(objectMapper.writeValueAsString(chatMessage));
				logger.debug(String.format("broadcasting \"%s\" to the following path \"%s\"",chatMessage.getMessageBody(),path));
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		publisher.publish("chatMessageBroadcast",objectMapper.writeValueAsString(broadcastMessage));
	}
	
	public void broadcastMessage(BroadcastMessage broadcastMessage){
		for(String path:broadcastMessage.getPaths())
			MetaBroadcaster.getDefault().broadcastTo(path, broadcastMessage.getMessage());
	}

	private Collection<User> getConversationDestinations(String conversationId) {
		ChatConversation conversation = (ChatConversation) repository.findById(conversationId,ChatConversation.class);
		return conversation.getParties();
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

	public ChatConversation getChatConversation(String id, boolean hidePasswords) {
		ChatConversation chatConversation = (ChatConversation) repository.findById(id, ChatConversation.class);
		if(hidePasswords){ 
			for(User user : chatConversation.getParties()) user.setPassword(null);
			for(edu.asu.krypton.model.persist.db.ChatMessage chatMessage : chatConversation.getMessages()) chatMessage.getSource().setPassword(null);
		}
		return chatConversation;
	}

}
