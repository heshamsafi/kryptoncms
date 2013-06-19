package edu.asu.krypton.model.persist.db;

import org.springframework.data.mongodb.core.mapping.Document;

import edu.asu.krypton.form.annotations.CheckBox;
import edu.asu.krypton.form.annotations.InputText;
import edu.asu.krypton.model.repository.Repository;

@Document
public class Menu implements DbEntity {
	@InputText(readOnly=true)
	private String id;
	@InputText
	private String name;
	@InputText
	private String url;
	@InputText
	private int order;
	@CheckBox
	private boolean admin;
	public String getId() {
		return id;
	}
	public Menu setId(String id) {
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
	@Override
	public void onDelete(Repository<?> repository) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onEdit(Repository<?> repository) {
		// TODO Auto-generated method stub
		
	}
}
