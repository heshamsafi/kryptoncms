package edu.asu.krypton.model.message_proxies;

public class ScaffoldMessage extends Message {
	private String id;
	private String className;
	private String actualEntity;
	private String action;//edit , modify or create
	public String getActualEntity() {
		return actualEntity;
	}
	public void setActualEntity(String actualEntity) {
		this.actualEntity = actualEntity;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
