package edu.asu.krypton.controllers.membership.social;

import java.security.Principal;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.TimelineOperations;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.asu.krypton.model.message_proxies.membership.social.twitter.MessageForm;

@Controller
@RequestMapping(value="/membership/social/twitter")
public class TwitterController extends edu.asu.krypton.controllers.Controller {
	
	@Inject
	private ConnectionRepository connectionRepository;
	
	// Yahoo Where On Earth ID representing the entire world
	private static final long WORLDWIDE_WOE = 1L;
	
	private final String DEFAULT_DIR   = "connect/twitter/";
	private final String MESSAGES_VIEW = DEFAULT_DIR+"messages";
	private final String FRIENDS_VIEW  = DEFAULT_DIR+"friends";
	private final String PROFILE_VIEW  = DEFAULT_DIR+"profile";
	private final String TIMELINE_VIEW = DEFAULT_DIR+"timeline";
	private final String TRENDS_VIEW   = DEFAULT_DIR+"trends";
	
	private final String DEFAULT_BODIES_DIR = "bodies/";
	
	
	@RequestMapping(value="/messages", method=RequestMethod.GET)
	public String inbox(ModelMap model,HttpServletRequest request) {
		model.addAttribute("directMessages", connectionRepository.findPrimaryConnection(Twitter.class).getApi().directMessageOperations().getDirectMessagesReceived());
		model.addAttribute("dmListType", "Received");
		model.addAttribute("messageForm", new MessageForm());
		model.addAttribute("mode", "inbox");
		return appropriateView(request, DEFAULT_BODIES_DIR+MESSAGES_VIEW, defaultView(model, MESSAGES_VIEW));
	}

	@RequestMapping(value="/messages/sent", method=RequestMethod.GET)
	public String sent(ModelMap model,HttpServletRequest request) {
		model.addAttribute("directMessages", connectionRepository.findPrimaryConnection(Twitter.class).getApi().directMessageOperations().getDirectMessagesSent());
		model.addAttribute("dmListType", "Sent");
		model.addAttribute("messageForm", new MessageForm());
		model.addAttribute("mode", "sent");
		return appropriateView(request, DEFAULT_BODIES_DIR+MESSAGES_VIEW, defaultView(model, MESSAGES_VIEW));
	}

	@RequestMapping(value="/messages", method=RequestMethod.POST)
	public String sent(Model model,MessageForm message,HttpServletRequest request) {
		connectionRepository.findPrimaryConnection(Twitter.class).getApi().directMessageOperations().sendDirectMessage(message.getTo(), message.getText());
		return appropriateView(request, DEFAULT_BODIES_DIR+MESSAGES_VIEW, defaultView(model, MESSAGES_VIEW));
	}
	
	@RequestMapping(value="/friends", method=RequestMethod.GET)
	public String friends(ModelMap model,HttpServletRequest request) {
		model.addAttribute("profiles", connectionRepository.findPrimaryConnection(Twitter.class).getApi().friendOperations().getFriends());
		return appropriateView(request, DEFAULT_BODIES_DIR+FRIENDS_VIEW, defaultView(model, FRIENDS_VIEW));
	}

	@RequestMapping(value="/followers", method=RequestMethod.GET)
	public String followers(ModelMap model, HttpServletRequest request) {
		model.addAttribute("profiles", connectionRepository.findPrimaryConnection(Twitter.class).getApi().friendOperations().getFollowers());
		return appropriateView(request, DEFAULT_BODIES_DIR+FRIENDS_VIEW, defaultView(model, FRIENDS_VIEW));
	}
	
	@RequestMapping(value="/profile",method=RequestMethod.GET)
	public String defaultView(Principal currentUser, ModelMap model,HttpServletRequest request) {
		Connection<Twitter> connection = connectionRepository.findPrimaryConnection(Twitter.class);
		if (connection == null) {
			appropriateView(request, DEFAULT_BODIES_DIR+DEFAULT_DIR, defaultView(model,DEFAULT_DIR));
		}
		model.addAttribute("profile", connection.getApi().userOperations().getUserProfile());
		return appropriateView(request, DEFAULT_BODIES_DIR+PROFILE_VIEW, defaultView(model, PROFILE_VIEW));
	}

	@RequestMapping(value="/search", method=RequestMethod.GET)
	public String showTrends(@RequestParam("query") String query, ModelMap model,HttpServletRequest request) {
		model.addAttribute("timeline", connectionRepository.findPrimaryConnection(Twitter.class).getApi().searchOperations().search(query).getTweets());
		return appropriateView(request, DEFAULT_BODIES_DIR+TIMELINE_VIEW, defaultView(model, TIMELINE_VIEW));
	}
	
	@RequestMapping(value="/timeline", method=RequestMethod.GET)
	public String showTimeline(ModelMap model,HttpServletRequest request) {
		return showTimeline("Home", model, request);
	}
	
	@RequestMapping(value="/timeline/{timelineType}", method=RequestMethod.GET)
	public String showTimeline(@PathVariable("timelineType") String timelineType, ModelMap model,HttpServletRequest request) {
		final TimelineOperations timelineOperations= connectionRepository.findPrimaryConnection(Twitter.class).getApi().timelineOperations();
		if (timelineType.equals("Home")) {
			model.addAttribute("timeline", timelineOperations.getHomeTimeline());
		} else if(timelineType.equals("User")) {
			model.addAttribute("timeline", timelineOperations.getUserTimeline());
		} else if(timelineType.equals("Mentions")) {
			model.addAttribute("timeline", timelineOperations.getMentions());
		} else if(timelineType.equals("Favorites")) {
			model.addAttribute("timeline", timelineOperations.getFavorites());
		}
		model.addAttribute("timelineName", timelineType);
		return appropriateView(request, DEFAULT_BODIES_DIR+TIMELINE_VIEW, defaultView(model, TIMELINE_VIEW));
	}
	

	@RequestMapping(value="/tweet", method=RequestMethod.POST)
	public String postTweet(String message,HttpServletRequest request,Model model) {
		connectionRepository.findPrimaryConnection(Twitter.class).getApi().timelineOperations().updateStatus(message);
		return appropriateView(request, DEFAULT_BODIES_DIR+TIMELINE_VIEW, defaultView(model,TIMELINE_VIEW));
	}
	

	@RequestMapping(value="/trends", method=RequestMethod.GET)
	public String showTrends(ModelMap model,HttpServletRequest request) {
		model.addAttribute("trends", connectionRepository.findPrimaryConnection(Twitter.class).getApi().searchOperations().getLocalTrends(WORLDWIDE_WOE));
		return appropriateView(request, DEFAULT_BODIES_DIR+TRENDS_VIEW, defaultView(model, TRENDS_VIEW));
	}
}
