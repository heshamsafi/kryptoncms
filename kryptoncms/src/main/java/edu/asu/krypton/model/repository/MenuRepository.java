package edu.asu.krypton.model.repository;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import edu.asu.krypton.model.message_proxies.MenuMessage;
import edu.asu.krypton.model.persist.db.MenuItem;

@Repository
public class MenuRepository extends edu.asu.krypton.model.repository.Repository<MenuItem>{

	public MenuRepository() {
		setPersistentClass(MenuItem.class);
	}

	
	
	@Override
	public void saveOrUpdate(MenuItem entity) {
		for(MenuItem menuItem : entity.getMenuItems())
			saveOrUpdate(menuItem);
		mongoTemplate.save(entity);
		for(MenuItem menuItem : entity.getMenuItems()){
			menuItem.setParentId(entity.getId());
			mongoTemplate.save(menuItem);
		}
	}



	public List<MenuItem> getItems(boolean admin){
		Query query = new Query();
		query.with(new org.springframework.data.domain.Sort("order"));
		query.addCriteria(Criteria.where("admin").is(admin));
		return mongoTemplate.find(query, getPersistentClass());
	}

	public void rearrangeMenu(MenuMessage menuMessage) {
		int i = 1;
		for(String id:menuMessage.getNewOrder()){
			Query query = new Query();
			query.addCriteria(Criteria.where("id").is(id));
			Update update = new Update().set("order", i);
			mongoTemplate.findAndModify(query, update, getPersistentClass());
			++i;
		}
	}



	public Object getRootItems(boolean admin) {
		Query query = new Query();
		query.with(new org.springframework.data.domain.Sort("order"));
		query.addCriteria(Criteria.where("admin").is(admin).andOperator(Criteria.where("parentId").is(null)));
		return mongoTemplate.find(query, getPersistentClass());
	}
	
	
}
