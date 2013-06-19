package edu.asu.krypton.model.message_proxies;

import edu.asu.krypton.model.persist.db.Comment;

public class OutBoundCommentProxy {
	private String id;
	private String parentId;
	private String parentType;
	private String content;
	private String username;
	private int noOfReplies;
	private String path;
	public String toString(){
		return String.format("id=%s,parentId=%s,parentType=%s,content=%s,username=%s,noOfReplies=%s",id,parentId,parentType,content,username,noOfReplies);
	}
	
	public OutBoundCommentProxy(){}
	
	public OutBoundCommentProxy(Comment commentEntity){
		setId(commentEntity.getId());
		setContent(commentEntity.getContent());
		if (commentEntity.getAuthor() != null)
			setUsername(commentEntity.getAuthor().getUsername());
		else setUsername("Anonymous");
		
//		setParentId(commentEntity.getParent().getId());
		
		setNoOfReplies(commentEntity.getComments().size());
		
		
		
//		setParentType(commentEntity.getParent().getClass().getSimpleName().indexOf("Comment")>-1?
//				"Comment":commentEntity.getParent().getClass().getSimpleName());
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentType() {
		return parentType;
	}

	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
}
