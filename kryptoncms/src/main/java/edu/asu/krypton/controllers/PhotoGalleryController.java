package edu.asu.krypton.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.krypton.exceptions.CustomRuntimeException;
import edu.asu.krypton.model.message_proxies.AlbumMessage;
import edu.asu.krypton.model.message_proxies.PhotoMessage;
import edu.asu.krypton.model.message_proxies.QueryMessage;
import edu.asu.krypton.service.AlbumService;
import edu.asu.krypton.service.PhotoService;

@Controller
@RequestMapping(value = "/photo")
public class PhotoGalleryController extends
		edu.asu.krypton.controllers.Controller {

	@Autowired(required=true)
	private PhotoService photoService;
	
	@Autowired(required=true)
	private AlbumService albumService;

	private final String PHOTO_GALLERY_VIEW = "photo_gallery";

	private final String DEFAULT_BODIES_DIR = "bodies/";
	
	private final String ALBUM_GALLERY_VIEW = "album_gallery";
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String defaultView(ModelMap model, HttpServletRequest request) {
		return appropriateView(request,
				DEFAULT_BODIES_DIR + ALBUM_GALLERY_VIEW,
				defaultView(model, ALBUM_GALLERY_VIEW));
	}
	
	@RequestMapping(value = "/photo", method = RequestMethod.GET)
	public String photoView(ModelMap model, HttpServletRequest request){
		model.addAttribute("album_number", request.getHeader("album_number"));
		return appropriateView(request,
				DEFAULT_BODIES_DIR + PHOTO_GALLERY_VIEW,
				defaultView(model, PHOTO_GALLERY_VIEW));
	}
	
	@RequestMapping(value = "/{parentType}/{parentId}", method = RequestMethod.GET)
	public @ResponseBody QueryMessage<PhotoMessage> getPhotos(@PathVariable String parentType, @PathVariable long parentId){
		QueryMessage<PhotoMessage> queryMessage = new QueryMessage<PhotoMessage>();
		
		try {
			queryMessage.setQueryResult(photoService.getProxyByParentId(parentId)).setSuccessful(true);
		} catch (CustomRuntimeException e) {
			queryMessage.setSuccessful(false).setErrorMessage(e.getMessage());
			e.printStackTrace();
		}

		return queryMessage;
	}

	public PhotoService getPhotoService() {
		return photoService;
	}

	public void setPhotoService(PhotoService photoService) {
		this.photoService = photoService;
	}
	
	public AlbumService getAlbumService() {
		return albumService;
	}

	public void setAlbumService(AlbumService albumService) {
		this.albumService = albumService;
	}

	@RequestMapping(value = "/albums", method = RequestMethod.GET)
	public @ResponseBody QueryMessage<AlbumMessage> getAlbums(){
		QueryMessage<AlbumMessage> queryMessage = new QueryMessage<AlbumMessage>();
		
		try {
			queryMessage.setQueryResult(albumService.getProxyForAll()).setSuccessful(true);
		} catch (CustomRuntimeException e) {
			queryMessage.setSuccessful(false).setErrorMessage(e.getMessage());
			e.printStackTrace();
		}

		return queryMessage;
	}

}
