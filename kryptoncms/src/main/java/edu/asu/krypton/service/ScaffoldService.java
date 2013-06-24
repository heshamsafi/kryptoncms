package edu.asu.krypton.service;

import java.io.IOException;

import org.atmosphere.cpr.MetaBroadcaster;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import edu.asu.krypton.model.message_proxies.ScaffoldMessage;
import edu.asu.krypton.model.persist.db.DbEntity;
import edu.asu.krypton.model.repository.Repository;

@Service
public class ScaffoldService {

	@Autowired(required=true)
	private Repository<DbEntity> repository;
	
	@Autowired(required=true)
	private MongoTemplate mongoTemplate;
	
	@Autowired(required=true)
	private ObjectMapper objectMapper;

	public void serviceScaffoldCommand(ScaffoldMessage scaffoldMessage) throws JsonGenerationException, JsonMappingException, IOException, ClassNotFoundException {
		Class<?> type = Class.forName(scaffoldMessage.getClassName());
		if(scaffoldMessage.getAction().equals("DELETE")){
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(scaffoldMessage.getId()));
//			mongoTemplate.remove(query, type);
			repository.delete(query, type);
		}else if(scaffoldMessage.getAction().equals("MODIFY") || scaffoldMessage.getAction().equals("CREATE")){//modify or create
			Object object = objectMapper.readValue(scaffoldMessage.getActualEntity(), type);
			mongoTemplate.save(object);
			scaffoldMessage.setActualEntity(objectMapper.writeValueAsString(object));
		}
		MetaBroadcaster.getDefault().broadcastTo("/form/echo", objectMapper.writeValueAsString(scaffoldMessage));
	}
	
}
