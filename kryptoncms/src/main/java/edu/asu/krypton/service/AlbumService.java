package edu.asu.krypton.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.krypton.exceptions.CustomRuntimeException;
import edu.asu.krypton.model.message_proxies.AlbumMessage;
import edu.asu.krypton.model.persist.db.Album;
import edu.asu.krypton.model.persist.db.Photo;
import edu.asu.krypton.model.repository.AlbumRepository;

@Service
public class AlbumService {

	@Autowired(required=true)
	private AlbumRepository albumRepository;
		
	public Album findById(Serializable id){
		return albumRepository.findById(id);
	}
	
	public void saveOrUpdate(Album entity) {
			albumRepository.saveOrUpdate(entity);
	}
	
	public List<Album> getAll(){
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
