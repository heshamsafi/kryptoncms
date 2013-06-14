package edu.asu.krypton.model.repository;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import edu.asu.krypton.controllers.PhotoGalleryController;
import edu.asu.krypton.model.persist.db.Album;
import edu.asu.krypton.model.persist.db.Comment;
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
		query.addCriteria(Criteria.where("parent.id").is(Integer.parseInt(parentId)));
		logger.debug("ALL PHOTOS = ");
		logger.debug("ALL PHOTOS = " + mongoTemplate.findAll(Photo.class));
		return mongoTemplate.find(query, Photo.class);
//		return mongoTemplate.findAll(Photo.class);
	}
}
