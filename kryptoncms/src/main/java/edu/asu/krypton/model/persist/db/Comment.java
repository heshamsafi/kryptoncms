package edu.asu.krypton.model.persist.db;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Comment extends Commentable {
	@Id
	private String id;

	private String content;
	
	private Date date;
	
	@DBRef
//	@JsonIgnore
	private User author;
	
	public Comment(){
		this.date = new Date();
	}
	
	@XmlAttribute
	public String getId() {
		return this.id;
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
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * 3alashan el debugging
	 */
	@Override
	public String toString(){
		return String.format("Comment(id=%s, content=%s)", getId(),getContent());
	}

	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

}
