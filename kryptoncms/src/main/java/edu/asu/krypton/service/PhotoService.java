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
import edu.asu.krypton.model.repository.PhotoRepository;

@Service
public class PhotoService extends CommentableService<Photo> {
	
	@Autowired(required = true)
	private PhotoRepository photoRepository;

	@Override
	@SessionDependant
	public Photo findById(Serializable id) {
		return photoRepository.findById(id);
	}

	@Override
	@SessionDependant
	public void saveOrUpdate(Photo entity) {
		try {
			photoRepository.saveOrUpdate(entity);
		} catch (CustomRuntimeException e) {
			e.printStackTrace();
		}
	}

	@SessionDependant
	public List<Photo> getAll() {
		return photoRepository.getAll();
	}

	public PhotoRepository getPhotoRepository() {
		return photoRepository;
	}

	public void setPhotoRepository(PhotoRepository photoRepository) {
		this.photoRepository = photoRepository;
	}

	@SessionDependant
	public List<Photo> getByParentId(Long parentId) {
		return photoRepository.getByParentId(parentId);

	}

	@SessionDependant
	public List<PhotoMessage> getProxyByParentId(Long parentId) throws CustomRuntimeException {
		return convertEntitiesToProxies(getByParentId(parentId));
	}

	private List<PhotoMessage> convertEntitiesToProxies(List<Photo> entities) {
		List<PhotoMessage> proxies = new ArrayList<PhotoMessage>();
		for (Photo entity : entities) {
			proxies.add(new PhotoMessage(entity));
		}
		return proxies;
	}
}
