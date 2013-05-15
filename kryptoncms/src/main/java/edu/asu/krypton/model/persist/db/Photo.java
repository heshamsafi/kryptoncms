package edu.asu.krypton.model.persist.db;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Photo extends Commentable{

	@Id
	private String id;
	
	private String path;

	@DBRef
	private Album parent;
	
	public Album getParent() {
		return parent;
	}

	public void setParent(Album parent) {
		this.parent = parent;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
