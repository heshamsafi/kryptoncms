package edu.asu.krypton.model.message_proxies;

import edu.asu.krypton.model.persist.db.Comment;
import edu.asu.krypton.service.SessionDependant;

public class OutBoundCommentProxy {
	private Long id;
	private Long parentId;
	private String parentType;
	private String content;
	private String username;
	private int noOfReplies;
	
	//for lazy loading
	public OutBoundCommentProxy(Comment commentEntity){
		setId(commentEntity.getId());
		setContent(commentEntity.getContent());
		if (commentEntity.getAuthor() != null)
			setUsername(commentEntity.getAuthor().getUsername());
		else setUsername("Anonymous");
		
		setParentId(commentEntity.getParent().getId());
		
		setNoOfReplies(commentEntity.getComments().size());
		
		
		
		setParentType(commentEntity.getParent().getClass().getSimpleName().indexOf("Comment")>-1?
				"Comment":commentEntity.getParent().getClass().getSimpleName());
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public int getNoOfReplies() {
		return noOfReplies;
	}

	public void setNoOfReplies(int noOfReplies) {
		this.noOfReplies = noOfReplies;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentType() {
		return parentType;
	}

	public void setParentType(String parentType) {
		this.parentType = parentType;
	}
	
	
}
