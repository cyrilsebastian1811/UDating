package com.neu.edu.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.neu.edu.model.Admin;
import com.neu.edu.model.Advert;
import com.neu.edu.model.Location;
import com.neu.edu.model.User;

public class AdminDao extends Dao{
	
	public AdminDao() {}
	
	public Admin get(String userID, String password) {
		Admin admin = null;
		try {
			begin();
			Criteria crit = getSession().createCriteria(Admin.class);
			crit.add(Restrictions.eq("userID", userID));
			admin = (Admin) crit.uniqueResult();
			if(admin!=null && !security.matches(password, admin.getPassword())) admin = null;
			commit();
		}catch(HibernateException exc) {
			exc.printStackTrace();
			rollback();
		}finally {
			close();
		}
		return admin;
	}
	
	public List<Advert> getAdverts() {
		List<Advert> adverts = null;
		try {
			begin();
			Query q = getSession().createQuery("from Advert");
			adverts = q.getResultList();
			commit();
		}catch(HibernateException exc) {
			exc.printStackTrace();
			rollback();
		}finally {
			close();
		}
		return adverts;
	}
}
