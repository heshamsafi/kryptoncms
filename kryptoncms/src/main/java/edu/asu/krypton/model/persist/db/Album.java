package edu.asu.krypton.model.persist.db;

import java.util.ArrayList;
import java.util.Collection;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Album {
	@org.springframework.data.annotation.Id
	private ObjectId id;
	
	private String title;
	
	@DBRef
	private Collection<Photo> photos = new ArrayList<Photo>();

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Collection<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(Collection<Photo> photos) {
		this.photos = photos;
	}
}
