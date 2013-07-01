package edu.asu.krypton.model.persist.db;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import edu.asu.krypton.model.repository.Repository;

/**
 * 
 * @author Nadeem
 *
 */
@Document
public class Photo extends Commentable implements DbEntity{

	@Id
	private String id;
	
	private String path;
	
	private String album;

//	@DBRef
//	private Album parent;
	
//	public Album getParent() {
//		return parent;
//	}

//	public void setParent(Album parent) {
//		this.parent = parent;
//	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
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

	@Override
	public void onDelete(Repository<?> repository) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEdit(Repository<?> repository) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInsert(Repository<?> repository) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void merge(DbEntity newObject) {
		// TODO Auto-generated method stub
		
	}
}
