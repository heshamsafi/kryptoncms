package edu.asu.krypton.model.persist.db;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@XmlRootElement
public class CommentComment extends Comment {

	@ManyToOne
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
