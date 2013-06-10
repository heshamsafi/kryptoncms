package edu.asu.krypton.model.persist.db;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@XmlRootElement
public class Article extends Commentable {
	@Id
	private String id;
	
	private String content;
	
	//sheltaha 3alashan el testing law 7ad la2aha commented yeb2a ana neseet araga3ha
	//e3mel ma3roof we sheel el comment elly 3ala @Column(unique=true)
	//@Column(unique=true)
	@Indexed(unique=true)
	private String title;
	private String description;
	private boolean obsolete;
	
	// momken ne7tag nzawed author ba3deen
	
	@Indexed
	private Date date;

	public Article(){
		this.date = new Date();
	}
	@XmlAttribute
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	@XmlAttribute
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@XmlAttribute
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@XmlAttribute
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	@XmlAttribute
	public boolean isObsolete() {
		return obsolete;
	}
	public void setObsolete(boolean obsolete) {
		this.obsolete = obsolete;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return String.format("[id=%s,content=%s,title=%s,description=%s,date=%s,comments=%s]",id,content,title,description,date,getComments());
	}
	
}
