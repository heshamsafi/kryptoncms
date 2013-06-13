package edu.asu.krypton.model.persist.db;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import edu.asu.krypton.form.annotations.InputText;

@Document
@XmlRootElement
public class User implements DbEntity {
	@Id
	@InputText(readOnly=true)
	private String id;
	
	@Indexed(unique=true)
	@InputText
	private String username;
	@InputText(readOnly=true)
	private String password;
	@InputText
	private BigInteger role;
	
	public User(){}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@XmlTransient
	public BigInteger getRole() {
		return role;
	}
	public void setRole(BigInteger role) {
		this.role = role;
	}
	
//	@XmlTransient
//	public Collection<Comment> getComments() {
//		return comments;
//	}
//
//
//	public void setComments(Collection<Comment> comments) {
//		this.comments = comments;
//	}

	
	@Override
	public boolean equals(Object obj) {
		User user = (User)obj;
		return user.getUsername().equals(this.getUsername());
	}
	
	@Override
	public String toString(){
		return String.format("[type=DbUser, id=%s , username=%s]", id,username);
	}
}
