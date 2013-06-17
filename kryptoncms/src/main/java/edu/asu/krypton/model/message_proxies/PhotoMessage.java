package edu.asu.krypton.model.message_proxies;

import org.slf4j.Logger;

import edu.asu.krypton.model.persist.db.Photo;
import edu.asu.krypton.model.repository.PhotoRepository;

public class PhotoMessage {
	
	private final static Logger logger = org.slf4j.LoggerFactory.getLogger(PhotoMessage.class);

	private String id;
	private String path;
	private String album;

	public PhotoMessage(Photo photoEntity) {
		setPath(photoEntity.getPath());
		setAlbum(photoEntity.getAlbum());
		setId(photoEntity.getId());
		logger.debug("New Photo : " + getPath() + " " + getAlbum() );
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
