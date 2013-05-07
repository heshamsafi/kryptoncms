package edu.asu.krypton.model.repository;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import edu.asu.krypton.controllers.PhotoGalleryController;
import edu.asu.krypton.model.persist.db.Album;
import edu.asu.krypton.model.persist.db.Photo;

@Repository
public class PhotoRepository extends edu.asu.krypton.model.repository.Repository<Photo>{
	
	public PhotoRepository(){
		setPersistentClass(Photo.class);
	}
	
	public List<Photo> getByParentId(Long parentId){
	return getDao().getSession().createCriteria(Photo.class).add(Restrictions.eq("parent.id", parentId)).list();
	}
}
