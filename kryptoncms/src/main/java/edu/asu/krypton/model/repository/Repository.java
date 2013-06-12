package edu.asu.krypton.model.repository;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import edu.asu.krypton.controllers.PhotoGalleryController;
import edu.asu.krypton.exceptions.CustomRuntimeException;

public class Repository<T> {
//	@Autowired(required=true)
//	protected DataAccessObject<T> dao;

	@Autowired
	protected MongoTemplate mongoTemplate;
	
	private Class<T> persistentClass;
	
	//private static final Logger logger = LoggerFactory.getLogger(Repository.class);
	public void deleteAll(){
		mongoTemplate.dropCollection(getPersistentClass());
	}
	public void delete(T entity){
		mongoTemplate.remove(entity);
	}

	public void saveOrUpdate(T entity) throws CustomRuntimeException {
		mongoTemplate.save(entity);
	}

	public T findById(Serializable id){
		return mongoTemplate.findById(id, getPersistentClass());
	}
	
	public List<T> getAll(){
		return mongoTemplate.findAll(getPersistentClass());
	}
	
	
//	public DataAccessObject<T> getDao() {
//		return dao;
//	}
//	public void setDao(DataAccessObject<T> dao) {
//		this.dao = dao;
//	}
	public Class<T> getPersistentClass() {
		return persistentClass;
	}
	public void setPersistentClass(Class<T> persistentClass){
		this.persistentClass = persistentClass;
	}
}
