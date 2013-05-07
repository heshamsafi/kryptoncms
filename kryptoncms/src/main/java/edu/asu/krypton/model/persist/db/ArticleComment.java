package edu.asu.krypton.model.persist.db;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@XmlRootElement
public class ArticleComment extends Comment {

	@ManyToOne
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
