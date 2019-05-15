package com.neu.edu.dao;

import java.util.logging.Level;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Dao {
	private static final  ThreadLocal sessionThread = new ThreadLocal();
	private static final Configuration conf = new Configuration().configure("hibernate.cfg.xml");
	private static final SessionFactory sessionFactory = conf.buildSessionFactory();
	
	protected BCryptPasswordEncoder security = new BCryptPasswordEncoder();
	
	protected Dao() {}
	
	public static Session getSession() {
		Session session = (Session) sessionThread.get();
		if(session==null) {
			session = sessionFactory.openSession();
			sessionThread.set(session);
		}
		return session;
	}
	
    protected void begin() {
        getSession().beginTransaction();
    }
    
    protected void commit() {
        getSession().getTransaction().commit();
    }
    
    protected void rollback() {
        try {
            getSession().getTransaction().rollback();
        } catch (HibernateException e) {
        	System.out.println(e);
        }finally {
        	try {
                getSession().close();
            } catch (HibernateException e) {
            	System.out.println(e);
            }finally {
            	sessionThread.set(null);
            }
        }
    }
    
    public static void close() {
    	System.out.println("_----------Closed");
        getSession().close();
        sessionThread.set(null);
    }
}
