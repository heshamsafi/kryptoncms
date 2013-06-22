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
	
	private String version;
	
	private String patches;
	@Indexed
	private Date date;
	
	@TextArea(applyCKEditor=true)
	private String content;
	
	@CheckBox
	private boolean obsolete;
	// momken ne7tag nzawed author ba3deen
	
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPatches() {
		return patches;
	}

	public void setPatches(String patches) {
		this.patches = patches;
	}
	
}
