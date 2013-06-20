package edu.asu.krypton.model.persist.db;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import edu.asu.krypton.form.annotations.CheckBox;
import edu.asu.krypton.form.annotations.InputText;
import edu.asu.krypton.form.annotations.TextArea;
import edu.asu.krypton.model.repository.Repository;

@Document
@XmlRootElement
public class Article extends Commentable implements DbEntity {
	@Id
	@InputText(readOnly=true)
	private String id;
	
	@Indexed(unique=true)
	@InputText
	private String title;
	@InputText
	private String description;

	@Indexed
	private Date date;
	
	@InputText
	private String commentMode = "custom";
	
	@TextArea(applyCKEditor=true)
	private String content;
	
	@CheckBox
	private boolean obsolete;
	// momken ne7tag nzawed author ba3deen
	
	@Indexed
	@CheckBox
	private boolean home;
	
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

	@Override
	public void onInsert(Repository<?> repository) {
		// TODO Auto-generated method stub
		
	}

	public boolean isHome() {
		return home;
	}

	public void setHome(boolean home) {
		this.home = home;
	}

	public String getCommentMode() {
		return commentMode;
	}

	public void setCommentMode(String commentMode) {
		this.commentMode = commentMode;
	}
	
}
