package edu.asu.krypton.model.persist.db;

public interface Commentable extends DbEntity {
	public void setId(Long id);
	public Long getId();
}
