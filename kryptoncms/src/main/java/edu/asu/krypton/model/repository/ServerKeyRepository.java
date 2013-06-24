package edu.asu.krypton.model.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import edu.asu.krypton.model.persist.db.ServerKey;

@Repository
public class ServerKeyRepository extends edu.asu.krypton.model.repository.Repository<ServerKey> {
	
	public ServerKeyRepository(){
		setPersistentClass(ServerKey.class);
	}

	@Autowired(required = true)
	protected MongoTemplate mongoTemplate;
	
	public synchronized boolean insertOrUpdateServerKey(ServerKey serverKey) {
		try{
			mongoTemplate.save(serverKey);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
	public ServerKey getServerKey(){
		List<ServerKey> serverKey = mongoTemplate.findAll(ServerKey.class);
		if(serverKey == null || serverKey.size()==0)
			return null;
		return serverKey.get(0);
	}
}
