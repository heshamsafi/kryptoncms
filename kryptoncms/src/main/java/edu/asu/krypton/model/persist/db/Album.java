package edu.asu.krypton.model.persist.db;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Album {

	@Id
	@GeneratedValue
	private Long id;
	
	private String title;
	
	@OneToMany(mappedBy="parent", fetch=FetchType.LAZY)
	private Collection<Photo> photos = new ArrayList<Photo>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
