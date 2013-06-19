package edu.asu.krypton.service;

import java.io.Serializable;

import edu.asu.krypton.model.persist.db.Commentable;
import edu.asu.krypton.model.repository.Repository;


/**
 * 
 * this was an interface originally ... but spring couldn't autowire the instances of the classes 
 * that implemented it ... when i changed it to an abstract class and changed implements to extends
 * it was fixed i still don't understand why that happened ... but it works so i can't complain ! :)
 * @author hesham
 *
 * @param <Entity>
 */

public abstract class CommentableService <Entity extends Commentable> {
	protected Repository<Entity> repository;
	public abstract Commentable findById(Serializable id);
	public void saveOrUpdate(Entity commentable) {
			repository.saveOrUpdate(commentable);
	}
}
