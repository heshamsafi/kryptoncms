package edu.asu.krypton.model.persist.db;

public interface Commentable extends DbEntity {
	public void setId(String id);
	public String getId();
}
