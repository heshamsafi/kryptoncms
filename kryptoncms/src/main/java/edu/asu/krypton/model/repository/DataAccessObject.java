package edu.asu.krypton.model.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DataAccessObject<T> {
	@Autowired(required=true)
	private SessionFactory sessionFactory;
	private Session session;
	
	public synchronized void openSession(){
		setSession(sessionFactory.openSession());
		getSession().beginTransaction();
	}
	
	public synchronized void killSession(boolean exitNicely){
		try{
			if(exitNicely) getSession().getTransaction().commit();
			getSession().close();
		}catch(Exception ex){
			//this exception is most likely due to re-killing a dead session.			
		}
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	
}
