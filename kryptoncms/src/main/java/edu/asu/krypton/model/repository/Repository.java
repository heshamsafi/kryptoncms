package edu.asu.krypton.model.repository;

import java.io.Serializable;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import edu.asu.krypton.model.persist.db.DbEntity;

@org.springframework.stereotype.Repository
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
	public void delete(DbEntity dbEntity){
		mongoTemplate.remove(dbEntity);
	}
	public void delete(Query query,Class<?> docClass){
		mongoTemplate.remove(query,docClass);
	}
	public void deleteById(ObjectId id){
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		mongoTemplate.remove(query,getPersistentClass());
	}
	public void deleteById(String id){
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		mongoTemplate.remove(query,getPersistentClass());
	}
	public void deleteById(String id,Class<?> docClass){
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		mongoTemplate.remove(query,docClass);
	}
	public void saveOrUpdate(T entity)  {
		mongoTemplate.save(entity);
	}
	public void saveOrUpdate(DbEntity entity)  {
		mongoTemplate.save(entity);
	}

	public T findById(Serializable id){
		return mongoTemplate.findById(id, getPersistentClass());
	}
	
	public Object findById(Serializable id,Class<?> type){
		return mongoTemplate.findById(id, type);
	}
	public Object findById(Serializable id,String type) throws ClassNotFoundException{
		return findById(id, Class.forName(type));
	}
	public Object findByQuery(Query query,String type){
		return findByQuery(query,type);
	}
	public Object findByQuery(Query query,Class type){
		return mongoTemplate.find(query, type);
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
