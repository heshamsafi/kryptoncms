package edu.asu.krypton.model.persist.db;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
//@AttributeOverrides(@AttributeOverride(name="name", column=@Column(name="name")))
@XmlRootElement
public class Article implements Commentable {
	@Id
	@GeneratedValue
	private Long id;
	
	private String content;
	
	//sheltaha 3alashan el testing law 7ad la2aha commented yeb2a ana neseet araga3ha
	//e3mel ma3roof we sheel el comment elly 3ala @Column(unique=true)
	//@Column(unique=true)
	private String title;
	private String description;
	
	
	@OneToMany(mappedBy="parent"
			,fetch=FetchType.LAZY
			)
	@Cascade({CascadeType.ALL})
	@JsonIgnore
	private Collection<ArticleComment> comments = new ArrayList<ArticleComment>();
	
	// momken ne7tag nzawed author ba3deen

	@XmlAttribute
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	@XmlAttribute
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@XmlAttribute
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@XmlAttribute
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@XmlTransient
	public Collection<ArticleComment> getComments() {
		return comments;
	}
	
	public void setComments(Collection<ArticleComment> comments) {
		this.comments = comments;
	}
}
