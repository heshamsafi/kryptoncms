package edu.asu.krypton.model.message_proxies;

import edu.asu.krypton.model.persist.db.Album;

public class AlbumMessage {

	private String id;
	private String title;
	
	public AlbumMessage(Album albumEntity) {
		setId(albumEntity.getId());
		setTitle(albumEntity.getTitle());
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
}
