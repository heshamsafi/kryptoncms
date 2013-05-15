package edu.asu.krypton.model.persist.db;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@XmlRootElement
public class CommentComment extends Comment {

	@DBRef
	@JsonIgnore
	private Comment parent;

	@XmlTransient
	public Commentable getParent() {
		return parent;
	}

	public void setParent(Commentable parent) {
		this.parent = (Comment) parent;
	}
}
