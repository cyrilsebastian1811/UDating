package com.neu.edu.dao;


import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.neu.edu.model.Login;

public class LoginDao extends Dao{
	
	LoginDao() {}
	
	public Login get(String userID, String password) {
		Login login = null;
		try {
			
			begin();
			Query q = getSession().createQuery("from Login where accountId=:userID");
			q.setString("userID", userID);
			login = (Login) q.uniqueResult();
			if(login!=null && !security.matches(password, login.getPassword())) login = null;
			commit();
			
		}catch(HibernateException exc) {
			rollback();
		}finally {
			close();
		}
		return login;
	}
	
	public boolean AccountIdExists(String userId) {
		boolean exists = false;
		try {
			
			begin();
			Criteria crit = getSession().createCriteria(Login.class);
			crit.add(Restrictions.eq("accountId", userId));
			if(crit.uniqueResult() != null) exists = true;
			commit();
			
		}catch(HibernateException exc) {
			rollback();
		}finally {
			close();
		}
		return exists;
	}
	
	public Login checkIds(String accountId, String emailId) {
		Login login = null;
		try {
			begin();
			Query q = getSession().createQuery("from Login where accountId=:accountId and emailId=:emailId");
			q.setString("accountId", accountId);
			q.setString("emailId", emailId);
			login = (Login) q.uniqueResult();
			commit();
		}catch(HibernateException exc) {
			rollback();
		}finally {
			close();
		}
		return login;
	}

	public boolean save(Login login) {
		boolean status = false;
		try {
			begin();
			getSession().save(login);
			commit();
			status = true;
		}catch(HibernateException exc) {
			exc.printStackTrace();
			rollback();
		}finally {
			close();
		}
		return status;
	}
	
//	login will be saved in sessionScope
	public boolean deleteByID(short id) {
		boolean status = false;
		try {
			begin();
			Login login = getSession().get(Login.class, id);
			getSession().delete(login);
			commit();
			status = true;
		}catch(HibernateException exc) {
			exc.printStackTrace();
			rollback();
		}finally {
			close();
		}
		return status;
	}
	
	public boolean updatePassword(String accountId, String emailId, String password) {
		boolean status = false;
		try {
			begin();
			Query q = getSession().createQuery("update Login set password=:password where accountId=:accountId and emailId=:emailId");
			q.setString("accountId", accountId);
			q.setString("emailId", emailId);
			q.setString("password", password);
			q.executeUpdate();
			commit();
			status = true;
		}catch(HibernateException exc) {
			exc.printStackTrace();
			rollback();
		}finally {
			close();
		}
		return status;
	}

}
