package edu.asu.krypton.model.persist.db;

import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.bind.annotation.XmlRootElement;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import edu.asu.krypton.form.annotations.InputText;
import edu.asu.krypton.form.annotations.Scaffold;
import edu.asu.krypton.model.repository.Repository;

@XmlRootElement
@Document
@Scaffold
public class ChatMessage implements DbEntity {
	@Id
	@InputText(readOnly=true)
	private String id;
	
	@InputText
	private String message;
	
	@DBRef
	private User source;

	@SuppressWarnings("unchecked")
	@Override
	public void onDelete(Repository<?> repository)
			throws ClassNotFoundException {
		Query query = new Query();
		query.addCriteria(Criteria.where("messages.$id").is(new ObjectId(getId())));
		for(ChatConversation chatConversation : ((ArrayList<ChatConversation>) repository.findByQuery(query, ChatConversation.class) )){
			Iterator<ChatMessage> iterator = chatConversation.getMessages().iterator();
			while(iterator.hasNext()){
				ChatMessage chatMessage = iterator.next();
				if(chatMessage.getId().equals(getId())){
					iterator.remove();
					break;
				}
			}
			repository.saveOrUpdate(chatConversation);
		}
	}

	@Override
	public void onEdit(Repository<?> repository) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInsert(Repository<?> repository) {
		// TODO Auto-generated method stub
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getSource() {
		return source;
	}

	public void setSource(User source) {
		this.source = source;
	}

	@Override
	public void merge(DbEntity newObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addOwned(DbEntity owned) {
		// TODO Auto-generated method stub
		
	}

	
}
