package edu.asu.krypton.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.krypton.exceptions.CustomRuntimeException;
import edu.asu.krypton.model.message_proxies.AlbumMessage;
import edu.asu.krypton.model.persist.db.Album;
import edu.asu.krypton.model.persist.db.Photo;
import edu.asu.krypton.model.repository.AlbumRepository;

/**
 * 
 * @author Nadeem
 *
 */
@Service
public class AlbumService {
	
	private final static Logger logger = org.slf4j.LoggerFactory.getLogger(AlbumService.class);

	@Autowired(required=true)
	private AlbumRepository albumRepository;
		
	public Album findById(Serializable id){
		return albumRepository.findById(id);
	}
	
	public void saveOrUpdate(Album entity) {
			albumRepository.saveOrUpdate(entity);
	}
	
	public List<Album> getAll(){
		List<Album> albums = albumRepository.getAll();
		
		for(Album album : albums){
			logger.debug("Album " + album.getTitle());
			for(Photo photo : album.getPhotos()){
				logger.debug("Photo " + photo.getPath());
			}
		}
		
		
		return albumRepository.getAll();
	}

	public AlbumRepository getAlbumRepository() {
		return albumRepository;
	}

	public void setAlbumRepository(AlbumRepository albumRepository) {
		this.albumRepository = albumRepository;
	}
	
	public List<AlbumMessage> getProxyForAll() throws CustomRuntimeException{
		return convertEntitiesToProxies(getAll());
	}
	
	private List<AlbumMessage> convertEntitiesToProxies(List<Album> entities){
		List<AlbumMessage> proxies = new ArrayList<AlbumMessage>();
		for(Album entity : entities){
			proxies.add(new AlbumMessage(entity));
		}
		return proxies;
	}
}
