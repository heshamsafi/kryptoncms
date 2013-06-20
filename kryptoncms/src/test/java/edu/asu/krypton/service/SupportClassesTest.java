package edu.asu.krypton.service;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.asu.krypton.model.persist.db.Article;
import edu.asu.krypton.model.persist.db.Comment;
import edu.asu.krypton.model.persist.db.Commentable;
import edu.asu.krypton.model.persist.db.Photo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SupportClassesTest {

	@Autowired
	private SupportClasses supportClasses;

	// @Autowired
	// private Article article;
	// @Autowired
	// private Comment comment;
	// @Autowired
	// private Photo photo;
	// @Autowired
	// private PhotoComment photoComment;

	@Autowired
	private ArticleService articleService;

	public ArticleService getArticleService() {
		return articleService;
	}

	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	public CommentService getCommentService() {
		return commentService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	public PhotoService getPhotoService() {
		return photoService;
	}

	public void setPhotoService(PhotoService photoService) {
		this.photoService = photoService;
	}

	@Autowired
	private CommentService commentService;
	@Autowired
	private PhotoService photoService;

	public SupportClasses getSupportClasses() {
		return supportClasses;
	}

	public void setSupportClasses(SupportClasses supportClasses) {
		this.supportClasses = supportClasses;
	}

	private final Map<String, SupportClasses> parentEntities = new HashMap<String, SupportClasses>();

	@Before
	public void init() {
		parentEntities.put(
				edu.asu.krypton.model.persist.db.Photo.class.getSimpleName(),
				new SupportClasses().setCommentable(
						edu.asu.krypton.model.persist.db.Photo.class)
						.setService(photoService));

		parentEntities.put(
				edu.asu.krypton.model.persist.db.Comment.class.getSimpleName(),
				new SupportClasses().setCommentable(
						edu.asu.krypton.model.persist.db.Comment.class)
						.setService(commentService));

		parentEntities.put(
				edu.asu.krypton.model.persist.db.Article.class.getSimpleName(),
				new SupportClasses().setCommentable(
						edu.asu.krypton.model.persist.db.Article.class)
						.setService(articleService));
	}

	@Test
	public void testPhoto() {

		String parentType = "Photo";
		Class<? extends Commentable> commentable = parentEntities.get(
				parentType).getCommentable();
		CommentableService<? extends Commentable> commentableService = parentEntities
				.get(parentType).getService();

		Assert.assertEquals(commentable, Photo.class);
		Assert.assertEquals(commentableService, photoService);

	}
	
	@Test
	public void testArticle() {

		String parentType = "Article";
		Class<? extends Commentable> commentable = parentEntities.get(
				parentType).getCommentable();
		CommentableService<? extends Commentable> commentableService = parentEntities
				.get(parentType).getService();

		Assert.assertEquals(commentable, Article.class);
		Assert.assertEquals(commentableService, articleService);

	}

	@Test
	public void testComment() {

		String parentType = "Comment";
		Class<? extends Commentable> commentable = parentEntities.get(
				parentType).getCommentable();
		CommentableService<? extends Commentable> commentableService = parentEntities
				.get(parentType).getService();

		Assert.assertEquals(commentable, Comment.class);
		Assert.assertEquals(commentableService, commentService);

	}


}
