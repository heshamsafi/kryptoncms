package edu.asu.krypton.model.repository;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.TransactionException;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.krypton.exceptions.CustomRuntimeException;
import edu.asu.krypton.model.persist.db.User;

public class Repository<T> {
	@Autowired(required=true)
	protected DataAccessObject<T> dao;
	
	private Class<T> persistentClass;
	
	//private static final Logger logger = LoggerFactory.getLogger(Repository.class);
	@SuppressWarnings("unchecked")
	public void deleteAll(){
		for(T element : (List<T>)getDao().getSession().createCriteria(User.class).list()){
			getDao().getSession().delete(element);
		}
	}
	public boolean delete(T entity){
		try{
			dao.getSession().delete(entity);
			return true;
		}catch(TransactionException ex){
			dao.getSession().getTransaction().rollback();
			ex.printStackTrace();
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return false;		
	}

	public boolean saveOrUpdate(T entity) throws CustomRuntimeException{
		try{
			try{
				getDao().getSession().saveOrUpdate(entity);
				
				//for session left-overs
			}catch (org.hibernate.NonUniqueObjectException e){
				getDao().getSession().merge(entity);
			}
			return true;
		}catch(TransactionException ex){
			ex.printStackTrace();
		}catch(ConstraintViolationException ex){
			throw new CustomRuntimeException((entity instanceof User)?"This username is Taken":"Duplicate Entry");
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return false;
	}
	@SuppressWarnings("unchecked")
	public List<T> findByExample(T example) {
		List<T> matches = null;
		try{
			Criteria criteria = dao.getSession().createCriteria(getPersistentClass());
			criteria.add(Example.create(example));
			matches =  (List<T>) criteria.list();
		}catch(TransactionException ex){
			dao.getSession().getTransaction().rollback();
			ex.printStackTrace();
		}catch(HibernateException ex){
			ex.printStackTrace();
		}catch(ClassCastException ex){
			ex.printStackTrace();
		}
		return matches;
	}
	@SuppressWarnings("unchecked")
	public T findById(Serializable id){
		T entity = null;
		try{
			entity = (T) dao.getSession().get(getPersistentClass(), id);
		}catch(TransactionException ex){
			dao.getSession().getTransaction().rollback();
			ex.printStackTrace();
		}catch(HibernateException ex){
			ex.printStackTrace();
		}catch(ClassCastException ex){
			ex.printStackTrace();
		}
		return entity;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getAll(){
		return getDao().getSession().createCriteria(getPersistentClass()).list();
	}
	
	@SuppressWarnings("unchecked")
	public <U> List<U> getAllOf(String propertyName){
		return getDao().getSession()
					   .createCriteria(getPersistentClass())
					   .setProjection(Projections.property(propertyName))
					   .list();
	}
	
	public DataAccessObject<T> getDao() {
		return dao;
	}
	public void setDao(DataAccessObject<T> dao) {
		this.dao = dao;
	}
	public Class<T> getPersistentClass() {
		return persistentClass;
	}
	public void setPersistentClass(Class<T> persistentClass){
		this.persistentClass = persistentClass;
	}
}
