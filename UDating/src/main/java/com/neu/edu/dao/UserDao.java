package com.neu.edu.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.neu.edu.model.Location;
import com.neu.edu.model.Login;
import com.neu.edu.model.Matched;
import com.neu.edu.model.User;

public class UserDao extends Dao{
	
	public UserDao() {}
	
	public User get(String accountId, String password) {
		User user = null;
		try {
			begin();
			Criteria crit = getSession().createCriteria(User.class);
			crit.add(Restrictions.eq("accountId", accountId));
			user = (User) crit.uniqueResult();
			if(user!=null && !security.matches(password, user.getPassword())) user = null;
			commit();
		}catch(HibernateException exc) {
			exc.printStackTrace();
			rollback();
		}finally {
			close();
		}
		return user;
	}
	
	public User getToUpdate(String userID) {
		User user = null;
		try {
			begin();
			Criteria crit = getSession().createCriteria(User.class);
			crit.add(Restrictions.eq("userID", userID));
			user = (User) crit.uniqueResult();
			commit();
		}catch(HibernateException exc) {
			exc.printStackTrace();
			rollback();
		}finally {
			close();
		}
		return user;
	}
	
	public User getByID(short id) {
		User user = null;
		try {
			begin();
			user = getSession().get(User.class, id);
			commit();
		}catch(HibernateException exc) {
			exc.printStackTrace();
			rollback();
		}finally {
			close();
		}
		return user;
	}
	
	public List<User> getUsers(short id) {
		List<User> users = null;
		try {
			begin();
			User user = getSession().get(User.class, id);
			Set<User> temp = user.getLocation().getUsers();
			users = new ArrayList<User>(temp);
			commit();
		}catch(HibernateException exc) {
			exc.printStackTrace();
			rollback();
		}finally {
			close();
		}
		return users;
	}
	
	
	
	public boolean save(User user) {
		boolean status = false;
		try {
			List<User> users;
			begin();
			getSession().save(user);
			
			Query q;
			q = getSession().createQuery("from User u where u.sex=:sexPreference and u.age<=:agePreference");
			q.setCharacter("sexPreference", user.getSexPreference());
			q.setShort("agePreference", user.getAgePreference());
			System.out.println("from User u where u.sex="+user.getSexPreference()+" and u.age<="+user.getAgePreference());
			users = q.list();
			for(User temp: users) {
				user.getLocation().getUsers().add(temp);
			}
			
			q = getSession().createQuery("from User u where u.sexPreference=:sex and u.agePreference>=:age");
			q.setCharacter("sex", user.getSex());
			q.setShort("age", user.getAge());
			users = q.list();
			for(User temp: users) {
				temp.getLocation().getUsers().add(user);
			}
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
	
	
	public boolean update(User u) {
		boolean status = false;
		try {
			begin();
			getSession().merge(u);
			status = true;
			commit();
		}catch(HibernateException exc) {
			exc.printStackTrace();
			rollback();
		}finally {
			close();
		}
		return status;
	}
	
	public boolean delete(User u) {
		boolean status = false;
		try {
			begin();
			Location loc = getSession().get(Location.class,u.getLocation().getId());
			loc.getUsers().remove(u);
			Query q = getSession().createQuery("from User");
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
