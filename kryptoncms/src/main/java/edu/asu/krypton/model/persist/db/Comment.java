package edu.asu.krypton.model.persist.db;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public abstract class Comment implements Commentable {
	@Id
	private String id;

	private String content;
	
	@DBRef
	@JsonIgnore
	private Collection<CommentComment> comments = new ArrayList<CommentComment>();
	
	@DBRef
	@JsonIgnore
	private User author;
	
	@XmlAttribute
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}

	@XmlAttribute
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public abstract void 	    setParent(Commentable parent);
	
	@XmlTransient
	public abstract Commentable getParent();
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * 3alashan el debugging
	 */
	@Override
	public String toString(){
		return String.format("Comment(id=%d, content=%s)", getId(),getContent());
	}
	@Transient
	@XmlTransient
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	
	@XmlTransient
	@Transient
	public Collection<CommentComment> getComments() {
		return comments;
	}
	public void setComments(Collection<CommentComment> comments) {
		this.comments = comments;
	}
	
}
