package edu.asu.krypton.model.persist.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/*
 * missing the relationship with the UserPrivilege entity, don't forget adding the new attribute
 * added in the inheriting entities in the @AttributeOverrides annotation
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Component implements DbEntity{

	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
