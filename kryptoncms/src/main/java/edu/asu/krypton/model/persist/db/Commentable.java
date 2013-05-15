package edu.asu.krypton.model.persist.db;

import java.util.ArrayList;
import java.util.Collection;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.data.mongodb.core.mapping.DBRef;

public abstract class Commentable implements DbEntity {
	public abstract void setId(String id);
	public abstract String getId();
	
	@DBRef
	@JsonIgnore
	private Collection<Comment> comments = new ArrayList<Comment>();
	
	public Collection<Comment> getComments() {
		return comments;
	}
	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}
}
