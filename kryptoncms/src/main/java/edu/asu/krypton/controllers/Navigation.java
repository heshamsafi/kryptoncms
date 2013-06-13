package edu.asu.krypton.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.cpr.MetaBroadcaster;
import org.atmosphere.cpr.Meteor;
import org.atmosphere.cpr.AtmosphereResource.TRANSPORT;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.krypton.model.message_proxies.ChatMessage;
import edu.asu.krypton.model.message_proxies.InBoundCommentProxy;
import edu.asu.krypton.model.message_proxies.MenuMessage;
import edu.asu.krypton.model.message_proxies.OutBoundCommentProxy;
import edu.asu.krypton.model.message_proxies.QueryMessage;
import edu.asu.krypton.model.persist.db.Comment;
import edu.asu.krypton.service.MenuService;

@org.springframework.stereotype.Controller
@RequestMapping(value="navigation")
public class Navigation extends Controller {
	
	private final String DEFAULT_VIEW = "navigation";
	private final String MENU_VIEW = "admin_nav";
	private final String DEFAULT_BODY_DIR = "bodies/";
	
	private static final Logger logger = LoggerFactory.getLogger(Navigation.class);
	
	@Autowired
	private MenuService menuService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String defaultView(ModelMap model,HttpServletRequest request)  {
		return appropriateView(request, DEFAULT_BODY_DIR+DEFAULT_VIEW, defaultView(model, DEFAULT_VIEW));
	}
	
	@RequestMapping(method = RequestMethod.GET,value="/menu/{role}")
	public String menus(ModelMap model,HttpServletRequest request,@PathVariable String role)  {
		model.addAttribute("items", menuService.getItems(role.equals("admin")));
		return DEFAULT_BODY_DIR+MENU_VIEW;
	}
	
	@RequestMapping(value="/menu/echo",method = RequestMethod.GET)
	@ResponseBody
	public synchronized void handshake(final AtmosphereResource atmosphereResource,@RequestParam(defaultValue="") String j_username) throws IOException {

		Meteor m = Meteor.build(atmosphereResource.getRequest()).addListener(new AtmosphereResourceEventListenerAdapter());
		String path = String.format("/menu/echo");

		BroadcasterFactory broadcasterFactory = BroadcasterFactory.getDefault();
		Broadcaster broadcaster = null;
		try {
			broadcaster = broadcasterFactory.get(path);
		} catch (IllegalStateException ex) {
			broadcaster = broadcasterFactory.lookup(path);
		}
		broadcaster.addAtmosphereResource(atmosphereResource);
		m.resumeOnBroadcast(m.transport() == TRANSPORT.LONG_POLLING)
				.suspend(-1);
	}

	@RequestMapping(value="/menu/echo",method = RequestMethod.POST)
	@ResponseBody
	public void broadcast(@RequestBody String requestBody,AtmosphereResource atmosphereResource) throws IOException {
		if (requestBody.equals("closing")) {
			atmosphereResource.resume();
			return;
		}
		logger.debug(requestBody);
		MenuMessage menuMessage = new ObjectMapper().readValue(requestBody,MenuMessage.class);
		if(menuMessage.getAction().equals("rearrange")) menuService.rearrangeMenu(menuMessage);
		if(menuMessage.getAction().equals("delete")) menuService.delete(menuMessage.getOperandId());
		MetaBroadcaster.getDefault().broadcastTo("/menu/echo", requestBody);
	}
}
