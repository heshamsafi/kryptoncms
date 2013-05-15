package edu.asu.krypton.model.persist.db;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/*
 * missing the relationship with the UserPrivilege entity, don't forget adding the new attribute
 * added in the inheriting entities in the @AttributeOverrides annotation
 */
@Document
public class Component implements DbEntity{
	@Id
	private String id;
	
	private String name;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
