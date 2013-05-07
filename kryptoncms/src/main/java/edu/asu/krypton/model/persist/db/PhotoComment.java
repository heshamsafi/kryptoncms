package edu.asu.krypton.model.persist.db;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class PhotoComment extends Comment{

	@ManyToOne
	private Photo parent;

	@Override
	public void setParent(Commentable parent) {
		this.parent = (Photo) parent;		
	}

	@Override
	public Commentable getParent() {
		return parent;
	}
}
