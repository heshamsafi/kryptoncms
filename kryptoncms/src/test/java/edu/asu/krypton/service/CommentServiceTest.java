package edu.asu.krypton.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.krypton.exceptions.CustomRuntimeException;
import edu.asu.krypton.model.persist.db.Article;
import edu.asu.krypton.model.persist.db.Comment;
import edu.asu.krypton.model.persist.db.User;


/**
 * @author hesham
 * this is great for prefilling the database (Y) aswell as testing
 */
//@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CommentServiceTest{
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private RegistrationService registrationService;
	
	private final int NUMBER_OF_ARTICLES = 10; 
	private final int NUMBER_OF_COMMENTS_PER_ARTICLE = 20;
	private final int NUMBER_OF_COMMENT_PER_COMMENT = 20;
	private final int NUMBER_OF_COMMENT_PER_COMMENT_PER_COMMENT = 20;
	
	@Test
	public void insertArticleComments(){
		List<User> allUsers = registrationService.getAllUsers(false);
		int numberOfUsers = allUsers.size();
		
		Article article;
		Comment comment;
		int userCounter = 0;
		for(int i=1;i<=NUMBER_OF_ARTICLES;i++){
			article = new Article();
			article.setContent(String.format("article#%d content",i));
			article.setDescription(String.format("article#%d description", i));
			article.setTitle(String.format("article#%d title", i));
			getArticleService().saveOrUpdate(article);
			for(int j=1;j<=NUMBER_OF_COMMENTS_PER_ARTICLE;j++){
				try {
					getCommentService().insert(article.getId(), Article.class.getSimpleName(),String.format("Comment#%d in article#%d", j,i),allUsers.get((userCounter++)%numberOfUsers));
				} catch (CustomRuntimeException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Test
	public void insertCommentComments(){
		List<User> allUsers = registrationService.getAllUsers(false);
		int numberOfUsers = allUsers.size();
		
		Comment comment = null,
				commentComment;
		int articleCounter = 1,
			commentCounter = 1,
			commentCommentCounter=1,
			userCounter	   = 0;
		
		List<Article> allArticles = articleService.getAll();
		for(Article article:allArticles){
			for(Comment articleComment : article.getComments()){
				for(int i=1;i<=NUMBER_OF_COMMENT_PER_COMMENT;i++){
					try {
//						Comment commentEntity = commentService.insert(comment.getParentId(), comment.getCommentableType(),comment.getContent(),user);

						comment = getCommentService().insert(articleComment.getId(), Comment.class.getSimpleName(),String.format("comment#%d for comment#%d for articleNo#%d",i,commentCounter,articleCounter),allUsers.get((userCounter++)%numberOfUsers));
						for(int j=1;j<=NUMBER_OF_COMMENT_PER_COMMENT_PER_COMMENT;j++){
							try {
								commentComment = getCommentService().insert(comment.getId(), Comment.class.getSimpleName(), String.format("comment#%d for comment#%d for comment#%d for articleNo#%d",i,commentCounter,commentCommentCounter,articleCounter),allUsers.get((userCounter++)%numberOfUsers));
							} catch (CustomRuntimeException e) {
								e.printStackTrace();
							}
							++commentCommentCounter;
						}
					} catch (CustomRuntimeException e) {
						e.printStackTrace();
					}
				}++commentCounter;
			}++articleCounter;
			commentCounter = 0;
		}
	}
	
	public CommentService getCommentService() {
		return commentService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	public ArticleService getArticleService() {
		return articleService;
	}

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}
}
