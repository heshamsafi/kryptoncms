package edu.asu.krypton.model.persist.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import edu.asu.krypton.model.repository.Repository;

@Document
public class ChatConversation implements DbEntity {

	@Id
	private String id;
	
	@DBRef
	private Collection<User> parties = new ArrayList<User>();
	
	@DBRef
	private Collection<ChatMessage> messages = new ArrayList<ChatMessage>();
	
	
	@Override
	public String toString() {
		return String.format("id = %s, parties = %s, messages = %s", id,parties,messages);
	}

	@Override
	public void onDelete(Repository<?> repository)
			throws ClassNotFoundException {
		Iterator<ChatMessage> iterator = messages.iterator();
		while(iterator.hasNext()){
			ChatMessage chatMessage = iterator.next();
			repository.delete(chatMessage);
			iterator.remove();
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

	public Collection<User> getParties() {
		return parties;
	}

	public void setParties(Collection<User> parties) {
		this.parties = parties;
	}

	public Collection<ChatMessage> getMessages() {
		return messages;
	}

	public void setMessages(Collection<ChatMessage> messages) {
		this.messages = messages;
	}

	@Override
	public void merge(DbEntity newObject) {
		// TODO Auto-generated method stub
		
	}

	
}
