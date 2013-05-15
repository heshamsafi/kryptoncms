package edu.asu.krypton.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.krypton.exceptions.CustomRuntimeException;
import edu.asu.krypton.model.message_proxies.InBoundCommentProxy;
import edu.asu.krypton.model.message_proxies.OutBoundCommentProxy;
import edu.asu.krypton.model.message_proxies.QueryMessage;
import edu.asu.krypton.model.persist.db.Comment;
import edu.asu.krypton.model.persist.db.CommentComment;
import edu.asu.krypton.model.persist.db.User;
import edu.asu.krypton.service.CommentService;
import edu.asu.krypton.service.RegistrationService;
import edu.asu.krypton.service.SessionDependant;

@Controller
@RequestMapping(value="/comments")
public class CommentsController extends edu.asu.krypton.controllers.Controller {
	
	private static final Logger logger = LoggerFactory.getLogger(CommentsController.class);
	
	@Autowired(required=true)
	private CommentService commentService;
	
	@Autowired(required=true)
	private RegistrationService registrationService;
	
	private final String COMMENTS_VIEW = "comments";
	private final String FACEBOOK_COMMENTS_VIEW = "connect/facebook/"+COMMENTS_VIEW;
	private final String BODIES_PREFIX = "bodies/";


	@RequestMapping(value="/socket/{parentType}/{parentId}",method = RequestMethod.GET)
	@ResponseBody
	public synchronized void handshake(
			final AtmosphereResource atmosphereResource
			,@PathVariable String parentType
			,@PathVariable long   parentId
			) throws IOException {
		logger.debug("shaking hands");
		logger.debug("parent type : " + parentType);
		logger.debug("parent Id : " + parentId );
		Meteor m = Meteor.build(atmosphereResource.getRequest())
						 .addListener(
								 new AtmosphereResourceEventListenerAdapter()
					     );
		String path = String.format("/comments/%s/%s",parentType,parentId);
		
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
	
	@RequestMapping(value="/socket/{parentType}/{parentId}",method = RequestMethod.POST)
	@ResponseBody
	@SessionDependant//for lazy loading
	public void submitComment(
		   	 @RequestBody String requestBody// i want to check it before de-serialializing it
		   	 //otherwise i would have used jackson's interceptor
			,@PathVariable String parentType
			,@PathVariable String parentId
			,AtmosphereResource atmosphereResource) throws CustomRuntimeException, JsonGenerationException, JsonMappingException, IOException{
		if(requestBody.equals("closing")){
			System.err.println("comments -- close request but i am too much of a dumbass to close -----------------------------------");
			atmosphereResource.resume();
			return;
		}
		InBoundCommentProxy comment = new ObjectMapper().readValue(requestBody, InBoundCommentProxy.class);
		Comment commentEntity = commentService.insert(comment.getParentId(), comment.getCommentableType(),comment.getContent());
		QueryMessage<OutBoundCommentProxy> queryMessage = new QueryMessage<OutBoundCommentProxy>();
		queryMessage.setSuccessful(true);
		List<OutBoundCommentProxy> list = new ArrayList<OutBoundCommentProxy>();
		list.add(new OutBoundCommentProxy(commentEntity));
		queryMessage.setQueryResult(list);
		String json = new ObjectMapper().writeValueAsString(queryMessage);
		String path = String.format("/comments/%s/%s",
				parentType,
				parentId
			);
		MetaBroadcaster.getDefault().broadcastTo(path,json);
		System.out.println("broadcasting to "+path+" this message "+json);
	}
	
	
	/**
	 * @param parentType
	 * @param parentId
	 * @return Comments
	 * acts as a web service rather than a controller action to decouple the commenting module from 
	 * other commentable modules 
	 */
	@RequestMapping(value="/{parentType}/{parentId}",method=RequestMethod.GET)
	public @ResponseBody QueryMessage<OutBoundCommentProxy> getComments(
				@PathVariable String parentType,
				@PathVariable String   parentId					){
		QueryMessage<OutBoundCommentProxy> queryMessage = new QueryMessage<OutBoundCommentProxy>();
		try{
			queryMessage.setQueryResult(commentService.getProxyByParentId(parentId, parentType))
						.setSuccessful(true);
		}catch(CustomRuntimeException ex){
			//TODO:we should have error codes not error messages !
			queryMessage.setSuccessful(false)
					    .setErrorMessage(ex.getMessage());
			ex.printStackTrace();
		}
		return queryMessage;
	}
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String defaultView(ModelMap model,HttpServletRequest request){
		return appropriateView(request, BODIES_PREFIX+COMMENTS_VIEW, defaultView(model,COMMENTS_VIEW));
	}
	
	@RequestMapping(value = "/facebook/{parentType}/{parentId}", method = RequestMethod.GET)
	public String facebook(
			@PathVariable String parentType,
			@PathVariable long   parentId, 
			Model model,
			HttpServletRequest request){
		model.addAttribute("parentType", parentType);
		model.addAttribute("parentId",parentId);
		return appropriateView(request, BODIES_PREFIX+FACEBOOK_COMMENTS_VIEW, defaultView(model,FACEBOOK_COMMENTS_VIEW));
	}

	public CommentService getCommentService() {
		return commentService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}
}
