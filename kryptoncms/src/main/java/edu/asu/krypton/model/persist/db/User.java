package edu.asu.krypton.model.persist.db;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity(name="tbl_user")
@XmlRootElement
public class User implements DbEntity {
	@Id @GeneratedValue
	private Long id;
	
	@Column(unique=true)
	private String username;
	private String password;
	
	@OneToOne(mappedBy="user")
	@Cascade({CascadeType.ALL})
	@JsonIgnore
	private Role role;
	
	@OneToMany(mappedBy="author")
	@Cascade({CascadeType.ALL})//ana 7atet de hna 3alashan el cascade bas.
	@JsonIgnore
	private Collection<Comment> comments = new ArrayList<Comment>();
	
//	@Transient
//	private boolean rememberMe;
	public User(){}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
//	public boolean isRememberMe() {
//		return rememberMe;
//	}
//	public void setRememberMe(boolean rememberMe) {
//		this.rememberMe = rememberMe;
//	}
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
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	@XmlTransient
	public Collection<Comment> getComments() {
		return comments;
	}


	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}

	
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
