package edu.asu.krypton.model.message_proxies;

public class InBoundCommentProxy {
	private String parentId;
	private String commentableType;
	private String content;
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getCommentableType() {
		return commentableType;
	}
	public void setCommentableType(String commentableType) {
		this.commentableType = commentableType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString(){
		return String.format("%s(parentId=%d, commentableType=%s, content=%s)"
				,this.getClass().getSimpleName()
				,getParentId()
				,getCommentableType()
				,getContent());
	}
}
