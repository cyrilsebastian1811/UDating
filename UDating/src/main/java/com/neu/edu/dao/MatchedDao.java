package com.neu.edu.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.neu.edu.model.Matched;
import com.neu.edu.model.User;

public class MatchedDao extends Dao{
	
	public List<User> get(User u) {
		List<User> users = null;
		try {
			begin();
			Set<User> temp = getSession().get(User.class, u.getId()).getMatched().getUsers();
			users = new ArrayList<User>(temp);
			commit();
		}catch(HibernateException exc) {
			exc.printStackTrace();
			rollback();
		}finally{
			close();
		}
		return users;
	}
	
	public short save(Matched matched) {
		short matchedId = -1;
		try {
			begin();
			getSession().save(matched);
			commit();
			matchedId = matched.getId();
		}catch(HibernateException exc) {
			exc.printStackTrace();
			rollback();
		}finally{
			close();
		}
		return matchedId;
	}
	
	public boolean addUsers(short userId, short likedId) {
		boolean status = false;
		try {
			begin();
			User user = getSession().get(User.class, userId);
			User liked = getSession().get(User.class, likedId);
			user.getLocation().getUsers().remove(liked);
			user.getMatched().getUsers().add(liked);
			commit();
			status = true;
		}catch(HibernateException exc) {
			exc.printStackTrace();
			rollback();
		}finally{
			close();
		}
		return status;
	}
}
