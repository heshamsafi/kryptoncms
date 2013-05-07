package edu.asu.krypton.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.asu.krypton.model.repository.DataAccessObject;

@Aspect
@Component
@SuppressWarnings("rawtypes")
public class DataRepositoryAspect {
	private final static Logger logger = LoggerFactory.getLogger(DataRepositoryAspect.class);
	
	@Autowired(required=true)
	private DataAccessObject dao;
	
	//@Before("sessionDependant()")
	private void openSession(boolean wasTheSessionAlreadyOpen){
		//bug fix.
		//to handle the case when SessionDependant methods call other SessionDependant methods
		//within their own context.
		//normally what happens is that 1 - session opens 2 - session re-opens 3 - session closes 
		//4 - the caller attempts to perform operations with the session being oblivious to 
		//the fact that it was closed and throws a nasty exception
		if(wasTheSessionAlreadyOpen) return;
		getDao().openSession();
		logger.info("session opened");
	}
	//@After("sessionDependant()")
	private void killSession(boolean wasTheSessionAlreadyOpen,boolean exitNicely){
		if(wasTheSessionAlreadyOpen) return;
		getDao().killSession(exitNicely);
		logger.info("session closed");
	}
	
	/**
	 * @param proceedingJoinPoint
	 * @throws Throwable
	 * 
	 * the session must be closed whether or not the SessionDependant method throws an Exception
	 * that is why an around pointcut is used
	 * because if before and after is used the session will not be closed
	 */
	@Around("sessionDependant()")
			//|| execution(protected final void org.hibernate.collection.AbstractPersistentCollection.read())")
			//this was an attempt to open the session for lazy loading automatically but it failed because hibernate is already compiled and its
			//beans are not spring managed(not created in the spring bean factory) and to make it work we will have to use load time weaving which
			//is quite complicated so i am just going to encapsulate lazy loading in SessionDependant functions 
	public Object aroundSession(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		boolean wasTheSessionAlreadyOpen;
		try{
			wasTheSessionAlreadyOpen = getDao().getSession().isOpen();
		} catch(NullPointerException ex){
			wasTheSessionAlreadyOpen = false;
		}
		Object returnVal;
		try{
			openSession(wasTheSessionAlreadyOpen);
			returnVal = proceedingJoinPoint.proceed();
		}catch(Throwable ex){
			killSession(wasTheSessionAlreadyOpen,false);
			throw ex;
		}
		killSession(wasTheSessionAlreadyOpen,true);
		return returnVal;
	}
	
	@Pointcut("@annotation(edu.asu.krypton.service.SessionDependant)")
	public void sessionDependant(){}
	
	public DataAccessObject getDao() {
		return dao;
	}
	public void setDao(DataAccessObject dao) {
		this.dao = dao;
	}
	
}
