package edu.asu.krypton.model.message_proxies;

import java.util.ArrayList;

import org.bson.types.ObjectId;

public class MenuMessage {
	private ArrayList<ObjectId> newOrder = new ArrayList<ObjectId>();
	private boolean admin;
	private ObjectId parentId;
	private String action;
	private ObjectId operandId;
	public ArrayList<ObjectId> getNewOrder() {
		return newOrder;
	}

	public void setNewOrder(ArrayList<ObjectId> newOrder) {
		this.newOrder = newOrder;
	}

	public ObjectId getParentId() {
		return parentId;
	}

	public void setParentId(ObjectId parentId) {
		this.parentId = parentId;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public ObjectId getOperandId() {
		return operandId;
	}

	public void setOperandId(ObjectId operandId) {
		this.operandId = operandId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
}
