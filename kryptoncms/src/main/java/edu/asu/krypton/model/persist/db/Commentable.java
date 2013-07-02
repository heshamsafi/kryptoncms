package edu.asu.krypton.model.persist.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import edu.asu.krypton.model.repository.Repository;

public abstract class Commentable implements DbEntity {
	public abstract void setId(String id);
	public abstract String getId();
	
	@DBRef
	@Indexed
//	@JsonIgnore
	private Collection<Comment> comments = new ArrayList<Comment>();
	
	public Collection<Comment> getComments() {
		return comments;
	}
	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}
	@Override
	public void onDelete(Repository<?> repository) throws ClassNotFoundException {
		Iterator<Comment> iterator =  getComments().iterator();
		while (iterator.hasNext()) {
			Comment comment = iterator.next();
			System.out.println(comment);
			repository.delete(comment);
			iterator.remove();
//			getComments().remove(comment);
		}
	}
	@Override
	public void onEdit(Repository<?> repository) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addOwned(DbEntity owned) {
		if(owned instanceof Comment){
			Comment ownedComment = (Comment) owned; 
			ownedComment.setParentId(getId());
			ownedComment.setParentType(this.getClass().getName());
			getComments().add(ownedComment);
		}
	}
}
