package edu.asu.krypton.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import edu.asu.krypton.model.persist.db.DbEntity;
import edu.asu.krypton.model.repository.Repository;

@Component
@Aspect
public class CascadeAspect {
	private final static Logger logger = LoggerFactory.getLogger(CascadeAspect.class);
	
	@Autowired(required=true)
	private MongoTemplate mongoTemplate;
	
	@Autowired(required=true)
	private Repository<DbEntity> repository;
	
	@Before("execution(public void edu.asu.krypton.model.repository.Repository.delete(..)) && args(query,docClass)")
	public void delete(Query query,Class<?> docClass) {
		DbEntity entity = (DbEntity) mongoTemplate.findOne(query, docClass);
		delete(entity);
	}
	
	@Before("execution(public void edu.asu.krypton.model.repository.Repository.delete(..)) && args(id,docClass)")
	public void delete(String id,Class<?> docClass) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		delete(query,docClass);
	}
	
	@Before("execution(public void edu.asu.krypton.model.repository.Repository.delete(..)) && args(dbEntity)")
	public void delete(DbEntity dbEntity) {
		try{
			dbEntity.onDelete(repository);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void create(DbEntity dbentity){}
	public void edit(DbEntity dbEntity){}
}
