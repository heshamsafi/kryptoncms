package edu.asu.krypton.model.repository;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import edu.asu.krypton.model.persist.db.Comment;
import edu.asu.krypton.model.persist.db.Commentable;
import edu.asu.krypton.service.SessionDependant;

@Repository
public class CommentRepository extends edu.asu.krypton.model.repository.Repository<Comment> {
	
	public CommentRepository(){
		setPersistentClass(Comment.class);
	}
	
	@SuppressWarnings("unchecked")
	@SessionDependant
	public List<Comment> getByParentId(Long parentId,Class<? extends Commentable> parentType){
		return getDao().getSession().createCriteria( parentType )
				.add( Restrictions.eq("parent.id", parentId) )
				.list();
	}
}
