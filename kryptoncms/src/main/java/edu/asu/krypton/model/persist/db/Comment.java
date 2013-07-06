package edu.asu.krypton.model.persist.db;

import java.util.Collection;
import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import edu.asu.krypton.form.annotations.InputText;
import edu.asu.krypton.form.annotations.Scaffold;
import edu.asu.krypton.model.repository.Repository;

@XmlRootElement
@Document
@Scaffold
public class Comment extends Commentable implements DbEntity {
	@Id
	@InputText(readOnly = true)
	private String id;

	@InputText
	private String content;

	private Date date;

	private String parentId;

	private String parentType;

	@DBRef
	@Indexed
	private User author;

	public Comment() {
		this.date = new Date();
	}

	@XmlAttribute
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString() 3alashan el debugging
	 */
	@Override
	public String toString() {
		return String.format("Comment(id=%s, content=%s)", getId(),
				getContent());
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentType() {
		return parentType;
	}

	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

	@Override
	public void onDelete(Repository<?> repository)
			throws ClassNotFoundException {
		try {
			super.onDelete(repository);
			final Commentable commentable = (Commentable) repository.findById(
					getParentId(), getParentType());
			@SuppressWarnings("unchecked")
			Collection<Comment> staleObjects = CollectionUtils.select(
					commentable.getComments(), new Predicate() {
						@Override
						public boolean evaluate(Object comment) {
							return ((Comment) comment).getId().equals(getId());
						}
					});

			for (Comment comment : staleObjects) {
				commentable.getComments().remove(comment);
			}
			repository.saveOrUpdate(commentable);
		} catch (Exception e) {

		}
	}

	@Override
	public void onInsert(Repository<?> repository) {
		// TODO Auto-generated method stub

	}

	@Override
	public void merge(DbEntity newObject) {
		Comment newComment = (Comment) newObject;
		setContent(newComment.getContent());
	}

}
