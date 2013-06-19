package edu.asu.krypton.service;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author hesham
 * cleans up lingering resources on application close
 */
public class CleanupContextListener implements ServletContextListener {
	
	private static final Logger logger = LoggerFactory.getLogger(CleanupContextListener.class);
	
	public void contextDestroyed(ServletContextEvent event) {
		killLingeringJdbcDrivers();
		killLingeringThreads();
	}
	private void killLingeringJdbcDrivers(){
        //deregister drivers manually
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                logger.info(String.format("deregistering jdbc driver: %s", driver));
            } catch (SQLException e) {
            	logger.info(String.format("Error deregistering driver %s", driver), e);
            }
        }
	}
	@SuppressWarnings("deprecation")
	private void killLingeringThreads(){
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
        for(Thread t:threadArray) {
            if(t.getName().contains("Abandoned connection cleanup thread")) {
                synchronized(t) {
                    t.stop(); //don't complain, it works
                }
            }
        }		
	}
	
	public void contextInitialized(ServletContextEvent event) {}
}
