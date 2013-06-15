package edu.asu.krypton.model.persist.db;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import edu.asu.krypton.form.annotations.CheckBox;
import edu.asu.krypton.form.annotations.InputText;

@Document
public class Menu {
	@InputText(readOnly=true)
	private ObjectId id;
	@InputText
	private String name;
	@InputText
	private String url;
	
	private int order;
	@CheckBox
	private boolean admin;
	public ObjectId getId() {
		return id;
	}
	public Menu setId(ObjectId id) {
		this.id = id;
		return this;
	}
	public String getName() {
		return name;
	}
	public Menu setName(String name) {
		this.name = name;
		return this;
	}
	public String getUrl() {
		return url;
	}
	public Menu setUrl(String url) {
		this.url = url;
		return this;
	}
	public boolean isAdmin() {
		return admin;
	}
	public Menu setAdmin(boolean admin) {
		this.admin = admin;
		return this;
	}
	public int getOrder() {
		return order;
	}
	public Menu setOrder(int order) {
		this.order = order;
		return this;
	}
}
