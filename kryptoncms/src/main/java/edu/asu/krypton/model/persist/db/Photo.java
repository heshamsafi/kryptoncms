package edu.asu.krypton.model.persist.db;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Photo implements Commentable{

	@Id
	@GeneratedValue
	private Long id;
	
	private String path;

	@ManyToOne
	private Album parent;
	
	@OneToMany(mappedBy="parent", fetch=FetchType.LAZY)
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

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
