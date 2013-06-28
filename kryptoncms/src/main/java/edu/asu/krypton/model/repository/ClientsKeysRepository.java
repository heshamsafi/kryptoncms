package edu.asu.krypton.model.repository;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import edu.asu.krypton.model.persist.db.AppsSecurityInfo;

@Repository
public class ClientsKeysRepository extends edu.asu.krypton.model.repository.Repository<AppsSecurityInfo>{

	public ClientsKeysRepository(){
		setPersistentClass(AppsSecurityInfo.class);
	}
	
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public synchronized boolean insertOrUpdateAppInfo(AppsSecurityInfo appInfo) {
		try{
			mongoTemplate.save(appInfo);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public boolean deleteIndex(AppsSecurityInfo appInfo) {
		try{
			mongoTemplate.remove(appInfo);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public AppsSecurityInfo getAppInfo(String appName){
			Query query = new Query();
			query.addCriteria(Criteria.where("appName").is(appName));
			return mongoTemplate.findOne(query, AppsSecurityInfo.class);
	}
	
	public AppsSecurityInfo getAppInfoByECCPair(BigInteger publicKey_x, BigInteger publicKey_y){
		Query query = new Query();
		query.addCriteria(Criteria.where("publicKey_x").is(publicKey_x));
		query.addCriteria(Criteria.where("publicKey_y").is(publicKey_y));
		return mongoTemplate.findOne(query, AppsSecurityInfo.class);
	}
	
	public AppsSecurityInfo getAppInfoByRSAPair(BigInteger n, BigInteger e){
		Query query = new Query();
		query.addCriteria(Criteria.where("n").is(n));
		query.addCriteria(Criteria.where("e").is(e));
		return mongoTemplate.findOne(query, AppsSecurityInfo.class);
	}
}
