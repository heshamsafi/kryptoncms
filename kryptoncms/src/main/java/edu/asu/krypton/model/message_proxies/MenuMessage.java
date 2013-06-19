package edu.asu.krypton.model.message_proxies;

import java.util.ArrayList;

import org.bson.types.ObjectId;

public class MenuMessage {
	private ArrayList<String> newOrder = new ArrayList<String>();
	private boolean admin;
	private String parentId;
	private String action;
	private String operandId;
	public ArrayList<String> getNewOrder() {
		return newOrder;
	}

	public void setNewOrder(ArrayList<String> newOrder) {
		this.newOrder = newOrder;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getOperandId() {
		return operandId;
	}

	public void setOperandId(String operandId) {
		this.operandId = operandId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
}
