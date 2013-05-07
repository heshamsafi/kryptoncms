package edu.asu.krypton.model.message_proxies;

import edu.asu.krypton.model.persist.db.Photo;

public class PhotoMessage {

	private Long id;
	private String path;
	private String album;

	public PhotoMessage(Photo photoEntity) {
		setPath(photoEntity.getPath());
		setAlbum(photoEntity.getParent().getTitle());
		setId(photoEntity.getId());
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
