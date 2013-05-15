package edu.asu.krypton.controllers.membership.social;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@Controller
@RequestMapping(value="/membership/social/facebook")
public class FacebookController extends edu.asu.krypton.controllers.Controller {
	
//	@Inject
	private ConnectionRepository connectionRepository;
	
	private final String DEFAULT_DIR  = "connect/facebook/";
	private final String PROFILE_VIEW = DEFAULT_DIR+"profile";
	private final String FRIENDS_VIEW = DEFAULT_DIR+"friends";
	private final String FEED_VIEW    = DEFAULT_DIR+"feed";
	private final String ALBUMS_VIEW  = DEFAULT_DIR+"albums";
	
	private final String DEFAULT_BODIES_PREFIX = "bodies/";
	
	@RequestMapping(value="profile",method=RequestMethod.GET)
	public String defaultView(ModelMap model,HttpServletRequest request) {
		Connection<Facebook> connection = connectionRepository.findPrimaryConnection(Facebook.class);
		if (connection == null) {
			return appropriateView(request, DEFAULT_BODIES_PREFIX+"access_denied", defaultView(model,"access_denied"));
		}
		model.addAttribute("profile", connection.getApi().userOperations().getUserProfile());
		return appropriateView(request, DEFAULT_BODIES_PREFIX+PROFILE_VIEW, defaultView(model, PROFILE_VIEW));
	}
	
	@RequestMapping(value="/friends", method=RequestMethod.GET)
	public String getFriendsList(ModelMap model,HttpServletRequest request) {
		Connection<Facebook> connection = connectionRepository.findPrimaryConnection(Facebook.class);
		if (connection == null) {
			return "redirect:/connect/facebook";
		}
		model.addAttribute("friends", connection.getApi().friendOperations().getFriendProfiles());
		return appropriateView(request, DEFAULT_BODIES_PREFIX+FRIENDS_VIEW, defaultView(model, FRIENDS_VIEW));
	}
	
	
	@RequestMapping(value="/feed", method=RequestMethod.GET)
	public String showFeed(ModelMap model,HttpServletRequest request) {
		model.addAttribute("feed", connectionRepository.findPrimaryConnection(Facebook.class).getApi().feedOperations().getFeed());
		return appropriateView(request, DEFAULT_BODIES_PREFIX+FEED_VIEW, defaultView(model,FEED_VIEW));
	}
	
	@RequestMapping(value="/feed", method=RequestMethod.POST)
	public String postUpdate(ModelMap model,String message,HttpServletRequest request) {
		connectionRepository.findPrimaryConnection(Facebook.class).getApi().feedOperations().updateStatus(message);
		return appropriateView(request, DEFAULT_BODIES_PREFIX+FEED_VIEW, defaultView(model,FEED_VIEW));
	}
	
	@RequestMapping(value="/albums", method=RequestMethod.GET)
	public String showAlbums(ModelMap model,HttpServletRequest request) {
		model.addAttribute("albums", connectionRepository.findPrimaryConnection(Facebook.class).getApi().mediaOperations().getAlbums());
		return appropriateView(request, DEFAULT_BODIES_PREFIX+ALBUMS_VIEW, defaultView(model, ALBUMS_VIEW));
	}
	
	@RequestMapping(value="/album/{albumId}", method=RequestMethod.GET)
	public String showAlbum(@PathVariable("albumId") String albumId, ModelMap model,HttpServletRequest request) {
		model.addAttribute("album",  connectionRepository.findPrimaryConnection(Facebook.class).getApi().mediaOperations().getAlbum(albumId));
		model.addAttribute("photos", connectionRepository.findPrimaryConnection(Facebook.class).getApi().mediaOperations().getPhotos(albumId));
		return appropriateView(request, DEFAULT_BODIES_PREFIX+ALBUMS_VIEW, defaultView(model, ALBUMS_VIEW));
	}
}
