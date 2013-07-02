package edu.asu.krypton.model.persist.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import edu.asu.krypton.form.annotations.CheckBox;
import edu.asu.krypton.form.annotations.InputText;
import edu.asu.krypton.model.repository.Repository;

@Document
public class MenuItem implements DbEntity {
	@Indexed
	@InputText(readOnly=true)
	private String id;
	@InputText
	private String name;
	@InputText
	private String url;
	@InputText
	private int order;
	@CheckBox
	@Indexed
	private boolean admin;
		
	@DBRef
	private Collection<MenuItem> menuItems = new ArrayList<MenuItem>();
	
	@Indexed
	private String parentId;
	
	public MenuItem(){
		setParentId(null);
	}
	
	//copy constructor
	public MenuItem(MenuItem menuItem){
		setName(menuItem.getName());
		setAdmin(menuItem.isAdmin());
		setName(menuItem.getName());
		setParentId(menuItem.getParentId());
		setUrl(menuItem.getUrl());
		setOrder(menuItem.getOrder());
		for(MenuItem subItem :menuItem.getMenuItems())
			getMenuItems().add(new MenuItem(subItem));
	}
	
	public String toString(){
		return String.format("id=%s, name=%s, url=%s, order=%d, admin=%s", id,name,url,order,admin);
	}
	
	public String getId() {
		return id;
	}
	public MenuItem setId(String id) {
		this.id = id;
		return this;
	}
	public String getName() {
		return name;
	}
	public MenuItem setName(String name) {
		this.name = name;
		return this;
	}
	public String getUrl() {
		return url;
	}
	public MenuItem setUrl(String url) {
		this.url = url;
		return this;
	}
	public boolean isAdmin() {
		return admin;
	}
	public MenuItem setAdmin(boolean admin) {
		this.admin = admin;
		return this;
	}
	public int getOrder() {
		return order;
	}
	public MenuItem setOrder(int order) {
		this.order = order;
		return this;
	}
	@Override
	public void onDelete(Repository<?> repository) {
		{
			Iterator<MenuItem> iterator = getMenuItems().iterator();
			while (iterator.hasNext()) {
				MenuItem menuItem = iterator.next();
				repository.delete(menuItem);
				
			}
		}
		if (getParentId() == null) return;
		MenuItem parent = (MenuItem) repository.findById(getParentId(),this.getClass());
		try{
			@SuppressWarnings("unchecked")
			Collection<MenuItem> staleObjects = CollectionUtils.select(parent.getMenuItems(), new Predicate() {
				@Override
				public boolean evaluate(Object menuItem) {
					return ((MenuItem) menuItem).getId().equals(getId());
				}
			});
			Iterator<MenuItem> iterator = staleObjects.iterator();
			while(iterator.hasNext()) {
				iterator.next();
				iterator.remove();
			}
			repository.saveOrUpdate(parent);
		}catch (Exception e) {
			
		}
		
	}
	@Override
	public void onEdit(Repository<?> repository) {
		// TODO Auto-generated method stub
		
	}
	public Collection<MenuItem> getMenuItems() {
		return menuItems;
	}
	public void setMenuItems(Collection<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public void onInsert(Repository<?> repository) {

	}

	@Override
	public void merge(DbEntity newObject) {
		MenuItem newMenuItem = (MenuItem)newObject;
		setName(newMenuItem.getName());
		setUrl(newMenuItem.getUrl());
		setOrder(newMenuItem.getOrder());
		setAdmin(newMenuItem.isAdmin());
	}

	@Override
	public void addOwned(DbEntity owned) {
		if (owned instanceof MenuItem) {
			MenuItem menuItem = (MenuItem)owned;
			menuItem.setParentId(getId());
			menuItems.add(menuItem);
		}
	}

}
