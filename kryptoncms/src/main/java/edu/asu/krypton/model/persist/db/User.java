package edu.asu.krypton.model.persist.db;

import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import edu.asu.krypton.form.annotations.InputText;
import edu.asu.krypton.model.repository.Repository;

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

	@Override
	public boolean equals(Object obj) {
		User user = (User)obj;
		return user.getUsername().equals(this.getUsername());
	}
	
	@Override
	public String toString(){
		return String.format("[type=DbUser, id=%s , username=%s, role=%s]", id,username,role);
	}


	@Override
	public void onDelete(Repository<?> repository) {
		@SuppressWarnings("unchecked")
		List<Comment> hisComments = (List<Comment>) repository.findByQuery(new Query().addCriteria(Criteria.where("author").is(this)), Comment.class);
		System.out.println("these comments will be deleted");
		for(Comment comment : hisComments){
			repository.delete(comment);
		}
		
	}


	@Override
	public void onEdit(Repository<?> repository) {
		// TODO Auto-generated method stub
		
	}
}
