package edu.asu.krypton.model.repository;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import edu.asu.krypton.model.persist.db.Album;
import edu.asu.krypton.model.persist.db.Photo;

@Repository
public class PhotoRepository extends edu.asu.krypton.model.repository.Repository<Photo>{
	
	private final static Logger logger = org.slf4j.LoggerFactory.getLogger(PhotoRepository.class);
	
	public PhotoRepository(){
		setPersistentClass(Photo.class);
	}
	
	public List<Photo> getByParentId(String parentId){
		//return getDao().getSession().createCriteria(Photo.class).add(Restrictions.eq("parent.id", parentId)).list();
		
		Query query = new Query();
//		query.addCriteria(Criteria.where("parent.id").is(parentId));
//		String albumTitle = "album1";
		query.addCriteria(Criteria.where("id").is(parentId));
//		query.addCriteria(Criteria.where("id").in(new AlbumRepository().findById(parentId).getPhotos()));
//		logger.debug("Album is = " + new AlbumRepository().findById(parentId));
		logger.debug("ALL PHOTOS = " + mongoTemplate.find(query, Album.class) );
		List<Album> albums = mongoTemplate.find(query, Album.class);
		logger.debug("Album  - photos: " + albums.get(0).getPhotos());
		String albumName = albums.get(0).getTitle();
		logger.debug("Album  - name: " + albumName);
		query = new Query();
		query.addCriteria(Criteria.where("album").is(albumName));
		
		List<Photo> photos = mongoTemplate.find(query, Photo.class);
		logger.debug("Photos : " + photos);
//		List<Photo> photos = (List<Photo>) albums.get(0).getPhotos();
//		for(Photo photo : photos){
//			logger.debug("Photo : " + photo.getPath());
//		}
		return photos;
//		return mongoTemplate.find(query, Photo.class);

		
		
//		logger.debug("Album title : " + albumRepository.findById(parentId));
//		return (List<Photo>)albumRepository.findById(parentId).getPhotos();
//		return mongoTemplate.findAll(Photo.class);
		
	}
}
