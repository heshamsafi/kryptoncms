package edu.asu.krypton.model.message_proxies;

import org.bson.types.ObjectId;

import edu.asu.krypton.model.persist.db.Album;

public class AlbumMessage {

	private ObjectId id;
	private String title;
	
	public AlbumMessage(Album albumEntity) {
		setId(albumEntity.getId());
		setTitle(albumEntity.getTitle());
	}
	
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
}
