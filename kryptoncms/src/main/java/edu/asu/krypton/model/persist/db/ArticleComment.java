package edu.asu.krypton.model.persist.db;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@XmlRootElement
public class ArticleComment extends Comment {

	@DBRef
	@JsonIgnore
	private Article parent;
	
	@XmlTransient 
	public Commentable getParent() {
		return (Commentable)parent;
	}

	public void setParent(Commentable parent) {
		this.parent = (Article) parent;
	}
}
