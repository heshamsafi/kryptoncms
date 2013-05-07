package edu.asu.krypton.model.message_proxies;

import edu.asu.krypton.model.persist.db.Album;

public class AlbumMessage {

	private Long id;
	private String title;
	
	public AlbumMessage(Album albumEntity) {
		setId(albumEntity.getId());
		setTitle(albumEntity.getTitle());
	}
	
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
}
