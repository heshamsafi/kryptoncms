package edu.asu.krypton.model.persist.db;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Photo implements Commentable{

	@Id
	private String id;
	
	private String path;

	@DBRef
	private Album parent;
	
	@DBRef
	private Collection<PhotoComment> comments = new ArrayList<PhotoComment>();
	
	public Collection<PhotoComment> getComments() {
		return comments;
	}

	public void setComments(Collection<PhotoComment> comments) {
		this.comments = comments;
	}

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
