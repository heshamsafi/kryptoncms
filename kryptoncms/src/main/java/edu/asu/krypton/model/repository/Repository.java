package edu.asu.krypton.model.repository;

import java.io.Serializable;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

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
	public void deleteById(ObjectId id){
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		mongoTemplate.remove(query,getPersistentClass());
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
