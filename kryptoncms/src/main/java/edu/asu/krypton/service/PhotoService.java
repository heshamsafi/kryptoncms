package edu.asu.krypton.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.krypton.controllers.PhotoGalleryController;
import edu.asu.krypton.exceptions.CustomRuntimeException;
import edu.asu.krypton.model.message_proxies.PhotoMessage;
import edu.asu.krypton.model.persist.db.Album;
import edu.asu.krypton.model.persist.db.Photo;
import edu.asu.krypton.model.repository.ArticleRepository;
import edu.asu.krypton.model.repository.PhotoRepository;

@Service
public class PhotoService extends CommentableService<Photo> {

	@Override
	public Photo findById(Serializable id) {
		return repository.findById(id);
	}

	@Override
	public void saveOrUpdate(Photo entity) {
		try {
			repository.saveOrUpdate(entity);
		} catch (CustomRuntimeException e) {
			e.printStackTrace();
		}
	}

	public List<Photo> getAll() {
		return repository.getAll();
	}

	public PhotoRepository getPhotoRepository() {
		return (PhotoRepository) repository;
	}

	public void setPhotoRepository(PhotoRepository photoRepository) {
		this.repository = photoRepository;
	}

	@SessionDependant
	public List<Photo> getByParentId(String parentId) {
		return ((PhotoRepository)repository).getByParentId(parentId);

	}

	@SessionDependant
	public List<PhotoMessage> getProxyByParentId(String parentId) throws CustomRuntimeException {
		return convertEntitiesToProxies(getByParentId(parentId));
	}

	private List<PhotoMessage> convertEntitiesToProxies(List<Photo> entities) {
		List<PhotoMessage> proxies = new ArrayList<PhotoMessage>();
		for (Photo entity : entities) {
			proxies.add(new PhotoMessage(entity));
		}
		return proxies;
	}
	
	@Autowired(required=true)
	public void setRepository(PhotoRepository repository){
		this.repository = repository;
	}
}
