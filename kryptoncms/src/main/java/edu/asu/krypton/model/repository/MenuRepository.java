package edu.asu.krypton.model.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.w3c.dom.css.Counter;

import edu.asu.krypton.model.message_proxies.MenuMessage;
import edu.asu.krypton.model.persist.db.Menu;

@Repository
public class MenuRepository extends edu.asu.krypton.model.repository.Repository<Menu>{

	public MenuRepository() {
		setPersistentClass(Menu.class);
	}

	public List<Menu> getItems(boolean admin){
		Query query = new Query();
		query.with(new org.springframework.data.domain.Sort("order"));
		return mongoTemplate.find(query, getPersistentClass());
	}

	public void rearrangeMenu(MenuMessage menuMessage) {
		int i = 1;
		for(ObjectId id:menuMessage.getNewOrder()){
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(id));
			Update update = new Update().set("order", i);
			mongoTemplate.findAndModify(query, update, getPersistentClass());
			++i;
		}
	}
	
	
}
