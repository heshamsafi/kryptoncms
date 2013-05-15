package edu.asu.krypton.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.krypton.exceptions.CustomRuntimeException;
import edu.asu.krypton.model.message_proxies.OutBoundCommentProxy;
import edu.asu.krypton.model.persist.db.Comment;
import edu.asu.krypton.model.persist.db.Commentable;
import edu.asu.krypton.model.persist.db.User;
import edu.asu.krypton.model.repository.CommentRepository;

@Service
public class CommentService extends edu.asu.krypton.service.CommentableService<Comment> {
	@Autowired(required=true)
	private CommentRepository commentRepository;
	
	//autowired at setter
	private ArticleService articleService;
	
	@Autowired(required=true)
	private RegistrationService registrationService;
	
	//each service class gets wired at setter and the instance 
	//goes into the parentEntities right after that
	//at the time this comment is written we have only ArticleService ... but the same goes
	//for any Commentable Service instance field for this Service class
	
	private final Map<String,SupportClasses> parentEntities = new HashMap<String,SupportClasses>();
	
	
	public CommentService(){
		parentEntities.put(
				//edu.asu.krypton.model.persist.db.CommentComment.class.getSimpleName(),
				edu.asu.krypton.model.persist.db.Comment.class.getSimpleName(),
				new SupportClasses().setCommentable(edu.asu.krypton.model.persist.db.CommentComment.class)
									.setService(this)
		);
		parentEntities.put(
				//edu.asu.krypton.model.persist.db.CommentComment.class.getSimpleName(),
				edu.asu.krypton.model.persist.db.Photo.class.getSimpleName(),
				new SupportClasses().setCommentable(edu.asu.krypton.model.persist.db.PhotoComment.class)
									.setService(this)
		);
	}
	
	@SessionDependant
	public List<Comment> getByParentId(String parentId,String parentType) throws CustomRuntimeException{
		try{
			Class<? extends Commentable> commentable = parentEntities.get(parentType).getCommentable();
			if (commentable == null) throw new NullPointerException();
			
			return commentRepository.getByParentId(parentId,commentable);
		}catch(NullPointerException ex){
			ex.printStackTrace();
			throw new CustomRuntimeException(parentType+" is not a Commentable Entity");	
		}
	}
	
	@SessionDependant
	public List<OutBoundCommentProxy> getProxyByParentId(String parentId,String parentType) throws CustomRuntimeException{
		return convertEntitiesToProxies(
				getByParentId(parentId, parentType)
		);
	}
	
	private List<OutBoundCommentProxy> convertEntitiesToProxies(List<Comment> entities){
		List<OutBoundCommentProxy> proxies = new ArrayList<OutBoundCommentProxy>();
		for(Comment entity : entities) proxies.add(new OutBoundCommentProxy(entity));
		return proxies;
	}
	
	public Comment insert(String parentId,String parentType,String commentContent) throws CustomRuntimeException{
		return insert(parentId,parentType,commentContent,registrationService.getLoggedInDbUser());
	}
	
	@SessionDependant
	public Comment insert(String parentId,String parentType,String commentContent,User author) throws CustomRuntimeException{
		edu.asu.krypton.service.CommentableService<?> service = parentEntities.get(parentType).getService();
		Comment comment = parentEntities.get(parentType).instantiateDbEntity();
		comment.setParent(service.findById(parentId));
		comment.setContent(commentContent);
		comment.setAuthor(author);
		saveOrUpdate(comment);
		return comment;
	}
	
	@Override
	@SessionDependant
	public Comment findById(Serializable id) {
		return commentRepository.findById(id);
	}

	@Override
	@SessionDependant
	public void saveOrUpdate(Comment entity) {
		try {
			commentRepository.saveOrUpdate(entity);
		} catch (CustomRuntimeException e) {
			e.printStackTrace();
		}
	}
	
	public CommentRepository getCommentRepository() {
		return commentRepository;
	}

	public void setCommentRepository(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}

	public ArticleService getArticleService() {
		return articleService;
	}
	
	@Autowired(required=true)
	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
		
		parentEntities.put(
				//edu.asu.krypton.model.persist.db.ArticleComment.class.getSimpleName()
				edu.asu.krypton.model.persist.db.Article.class.getSimpleName(),
				new SupportClasses().setCommentable(edu.asu.krypton.model.persist.db.ArticleComment.class)
									.setService(articleService)
		);
	}
}
