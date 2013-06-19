package edu.asu.krypton.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.atmosphere.cpr.MetaBroadcaster;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.krypton.exceptions.CustomRuntimeException;
import edu.asu.krypton.model.message_proxies.OutBoundCommentProxy;
import edu.asu.krypton.model.message_proxies.QueryMessage;
import edu.asu.krypton.model.persist.db.Comment;
import edu.asu.krypton.model.persist.db.Commentable;
import edu.asu.krypton.model.persist.db.User;
import edu.asu.krypton.model.repository.CommentRepository;

@Service
public class CommentService extends
		edu.asu.krypton.service.CommentableService<Comment> {

	// autowired at setter
	private ArticleService articleService;

	@Autowired(required = true)
	private RegistrationService registrationService;
	
	@Autowired(required=true)
	private ObjectMapper objectMapper;

	// each service class gets wired at setter and the instance
	// goes into the parentEntities right after that
	// at the time this comment is written we have only ArticleService ... but
	// the same goes
	// for any Commentable Service instance field for this Service class

	private final Map<String, SupportClasses> parentEntities = new HashMap<String, SupportClasses>();

	public CommentService() {
		parentEntities.put(
				// edu.asu.krypton.model.persist.db.CommentComment.class.getSimpleName(),
				edu.asu.krypton.model.persist.db.Comment.class.getSimpleName(),
				new SupportClasses().setCommentable(
						edu.asu.krypton.model.persist.db.Comment.class)
						.setService(this));
		parentEntities.put(
				// edu.asu.krypton.model.persist.db.CommentComment.class.getSimpleName(),
				edu.asu.krypton.model.persist.db.Photo.class.getSimpleName(),
				new SupportClasses().setCommentable(
						edu.asu.krypton.model.persist.db.Photo.class)
						.setService(this));
	}

	public Collection<Comment> getByParentId(String parentId,String parentType) throws CustomRuntimeException{
		try{
			Class<? extends Commentable> commentable = parentEntities.get(parentType).getCommentable();
			if (commentable == null) throw new NullPointerException();
			Commentable commentableEntity = (Commentable)parentEntities.get(parentType).getService().findById(parentId);
			return commentableEntity.getComments();
		}catch(NullPointerException ex){
			ex.printStackTrace();
			throw new CustomRuntimeException(parentType+" is not a Commentable Entity");	
		}
	}

	public Collection<OutBoundCommentProxy> getProxyByParentId(String parentId,
			String parentType) throws CustomRuntimeException {
		return convertEntitiesToProxies(getByParentId(parentId, parentType));
	}

	private Collection<OutBoundCommentProxy> convertEntitiesToProxies(
			Collection<Comment> entities) {
		List<OutBoundCommentProxy> proxies = new ArrayList<OutBoundCommentProxy>();
		for (Comment entity : entities)
			proxies.add(new OutBoundCommentProxy(entity));
		return proxies;
	}

	public Comment insert(String parentId, String parentType,
			String commentContent) throws CustomRuntimeException {
		return insert(parentId, parentType, commentContent,registrationService.getLoggedInDbUser());
	}

	public Comment insert(String parentId, String parentType,String commentContent, User author) throws CustomRuntimeException {
		 SupportClasses support = parentEntities.get(parentType);
		 Comment comment = new Comment();
		 comment.setContent(commentContent);
		 comment.setAuthor(author);
		
		 Commentable commentable =  support.getService().findById(parentId);
		 commentable.getComments().add(comment);
		 comment.setParentId(commentable.getId());
		 comment.setParentType(commentable.getClass().getName());
		 saveOrUpdate(comment);
		 support.getService().saveOrUpdate(commentable);
		 return comment;
	}
	
	public void broadcastCommment(OutBoundCommentProxy proxy) throws JsonGenerationException, JsonMappingException, IOException {
		System.out.println(proxy.toString());
		QueryMessage<OutBoundCommentProxy> queryMessage = new QueryMessage<OutBoundCommentProxy>();
		queryMessage.setSuccessful(true);
		List<OutBoundCommentProxy> list = new ArrayList<OutBoundCommentProxy>();
		list.add(proxy);
		queryMessage.setQueryResult(list);
		String json = objectMapper.writeValueAsString(queryMessage);
//		String path = String.format("/comments/%s/%s", proxy.getParentType(), proxy.getParentId());
		MetaBroadcaster.getDefault().broadcastTo(proxy.getPath(), json);
		System.out.println("broadcasting to " + proxy.getPath() + " this message " + json);
	}

	@Override
	public Comment findById(Serializable id) {
		return repository.findById(id);
	}
	
	@Autowired(required=true)
	public void setRepository(CommentRepository repository){
		this.repository = repository;
	}

	public ArticleService getArticleService() {
		return articleService;
	}

	@Autowired(required = true)
	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;

		parentEntities.put(
				// edu.asu.krypton.model.persist.db.ArticleComment.class.getSimpleName()
				edu.asu.krypton.model.persist.db.Article.class.getSimpleName(),
				new SupportClasses().setCommentable(
						edu.asu.krypton.model.persist.db.Article.class)
						.setService(articleService));
	}

}
