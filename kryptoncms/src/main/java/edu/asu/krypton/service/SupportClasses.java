package edu.asu.krypton.service;

import edu.asu.krypton.model.persist.db.Comment;
import edu.asu.krypton.model.persist.db.Commentable;


public class SupportClasses {
	private Class<? extends Commentable> commentable;
	private CommentableService service;
	public Class<? extends Commentable> getCommentable() {
		return commentable;
	}
	public SupportClasses setCommentable(Class<? extends Commentable> commentable) {
		this.commentable = commentable;
		return this;
	}
	public CommentableService getService() {
		return service;
	}
	public SupportClasses setService(CommentableService service) {
		this.service = service;
		return this;
	}
	public Comment instantiateDbEntity(){
		try {
			return (Comment) this.getCommentable().newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
