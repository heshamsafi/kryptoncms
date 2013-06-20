package edu.asu.krypton.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.atmosphere.cpr.AtmosphereResource;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.krypton.model.message_proxies.ChatMessage;
import edu.asu.krypton.service.RegistrationService;
import edu.asu.krypton.service.atmosphere.chat.ChatService;
import edu.asu.krypton.service.redis.Publisher;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "/chat")
public class ChatController extends edu.asu.krypton.controllers.Controller {
	private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
	
	@Autowired(required=true)
	private ChatService chatService;
	
	@Autowired(required=true)
	private RegistrationService registrationService;
	
	@Autowired(required=true)
	private Publisher publisher;
	
	private final String CHAT_VIEW		    = "chat";
	private final String DEFAULT_BODIES_DIR = "bodies/";
	
	@RequestMapping(method = RequestMethod.GET)
	public String defaultView(ModelMap model,HttpServletRequest request) {
		logger.debug("chat page invoked !!!");
		model.addAttribute("usernames", registrationService.getAllUsernames(true));
		return DEFAULT_BODIES_DIR+CHAT_VIEW;
	}

	
	@RequestMapping(value="/echo",method = RequestMethod.GET)
	@ResponseBody
	public synchronized void handshake(final AtmosphereResource atmosphereResource,@RequestParam(defaultValue="") String j_username) throws IOException {
		logger.debug("i \"handshake\" got a request");
		edu.asu.krypton.model.persist.db.User user = getRegistrationService().getLoggedInDbUser();
		if(user == null) user = getRegistrationService().findUserByUsername(j_username);
		chatService.handShake(user,atmosphereResource);
	}

	@RequestMapping(value="/echo",method = RequestMethod.POST)
	@ResponseBody
	public void broadcast(@RequestBody String requestBody,AtmosphereResource atmosphereResource) throws IOException {
		String username;
		logger.debug("i \"broadcast\" got this message "+requestBody);
		if(requestBody.equals("closing")){
			System.err.println("chat -- close request but i am too much of a dumbass to close -----------------------------------");
			atmosphereResource.resume();
			return;
		}
			
		ObjectMapper objectMapper = new ObjectMapper();
		ChatMessage chatMessage = objectMapper.readValue(requestBody, ChatMessage.class);
		try{
			username = getRegistrationService().findUserByUsername(chatMessage.getSource()).getUsername();
								//.getLoggedInUser().getUsername();
		} catch(NullPointerException ex){
			username = "Anonymous";
		}
		chatMessage.setSource(username);
		logger.debug(chatMessage.getSource()+ " : sent a message");
		publisher.publish("chatMessageBroadcast",objectMapper.writeValueAsString(chatMessage));
//		chatService.broadcast(chatMessage);
	}
	
	public RegistrationService getRegistrationService() {
		return registrationService;
	}

	public void setRegistrationService(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}

	public ChatService getChatService() {
		return chatService;
	}

	public void setChatService(ChatService chatService) {
		this.chatService = chatService;
	}

}