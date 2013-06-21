package edu.asu.krypton.model.persist.db;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import edu.asu.krypton.model.repository.Repository;

public class ChatMessage implements DbEntity {
	@Id
	private String id;
	
	private String message;
	
	@DBRef
	private User source;

	@Override
	public void onDelete(Repository<?> repository)
			throws ClassNotFoundException {
		// TODO Auto-generated method stub
		
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

	
}
