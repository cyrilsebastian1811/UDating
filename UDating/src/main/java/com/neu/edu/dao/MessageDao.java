package com.neu.edu.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.neu.edu.model.Matched;
import com.neu.edu.model.Message;
import com.neu.edu.model.User;

public class MessageDao extends Dao{
	
	public List<Message> get(short fromUserId, short toUserId, int msgCount) {
		List<Message> messages = null;
		try {
			begin();
			Query q = getSession().createQuery("from Message where (fromId=:fromId and toId=:toId) or ((fromId=:toId and toId=:fromId))");
			q.setShort("fromId", fromUserId);
			q.setShort("toId", toUserId);
			messages = q.list();
			commit();
		}catch(HibernateException exc) {
			exc.printStackTrace();
			rollback();
		}finally{
			close();
		}
		return messages;
	}
	
	public boolean save(Message msg) {
		boolean success = false;
		try {
			begin();
			getSession().save(msg);
			commit();
			success = true;
		}catch(HibernateException exc) {
			exc.printStackTrace();
			rollback();
		}finally{
			close();
		}
		return success;
	}
}
