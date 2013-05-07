package edu.asu.krypton.model.persist.db;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/*
 * missing the relationship with the RegisteredUser entity
 */
//Hesham : I am sorry toba but i am going to have to mess with your code :)

// i thought about generics but i am not sure if and how hibernate can handle that .... lets try it
// later when we have more time to spare
@Inheritance(
		//it cannot be Single table with a discriminator column 
		//bcz that would violate foreign key constraint
		strategy=InheritanceType.JOINED
)
//@DiscriminatorColumn(name="CommentType")
//@MappedSuperclass//rather than an entity
@Entity
public abstract class Comment implements Commentable {
	@Id
	@GeneratedValue
	private Long id;

	private String content;
	
	@OneToMany(mappedBy="parent")
	@Cascade({CascadeType.ALL})
	@JsonIgnore
	private Collection<CommentComment> comments = new ArrayList<CommentComment>();
	
	@ManyToOne
	@JsonIgnore
	private User author;
	
	@XmlAttribute
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@XmlAttribute
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public abstract void 	    setParent(Commentable parent);
	
	@XmlTransient
	public abstract Commentable getParent();
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * 3alashan el debugging
	 */
	@Override
	public String toString(){
		return String.format("Comment(id=%d, content=%s)", getId(),getContent());
	}
	@Transient
	@XmlTransient
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	
	@XmlTransient
	@Transient
	public Collection<CommentComment> getComments() {
		return comments;
	}
	public void setComments(Collection<CommentComment> comments) {
		this.comments = comments;
	}
	
}
