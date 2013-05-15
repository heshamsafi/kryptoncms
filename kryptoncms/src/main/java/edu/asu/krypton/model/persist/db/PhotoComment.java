package edu.asu.krypton.model.persist.db;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PhotoComment extends Comment{

	@DBRef
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
