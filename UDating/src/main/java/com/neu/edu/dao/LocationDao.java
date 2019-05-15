package com.neu.edu.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.neu.edu.model.Location;
import com.neu.edu.model.Matched;
import com.neu.edu.model.User;

public class LocationDao extends Dao{
	
	public LocationDao() {}
	
	public Location get(String street, String city, String country) {
		Location location = null;
		try {
			
			begin();
			Criteria crit = getSession().createCriteria(Location.class);
			crit.add(Restrictions.ilike("street", street));
			crit.add(Restrictions.ilike("city", city));
			crit.add(Restrictions.ilike("country", country));
			location = (Location) crit.uniqueResult();
			commit();
			
		}catch(HibernateException exc){
			exc.printStackTrace();
			rollback();
		} finally {
			close();
		}
		return location;
	}
	
	public boolean addUser(User user) {
		boolean status = false;
		try {
			
			begin();
			Location loc = user.getLocation();
			Query q = getSession().createQuery("from Location where street=:street and city=:city and country=:country");
			q.setString("street", loc.getStreet());	q.setString("city", loc.getCity());	q.setString("country", loc.getCountry());
			Location temp = (Location) q.uniqueResult();
			if(temp==null) {
				loc.getUsers().add(user);
				getSession().save(loc);
			}else {
				user.setLocation(temp);
				getSession().save(user);
				temp.getUsers().add(user);
			}
			commit();
			status = true;
			
		}catch(HibernateException exc){
			exc.printStackTrace();
			rollback();
		} finally {
			close();
		}
		return status;
	}
	
	
	public boolean save(Location location){
		boolean status = false;
		try {
			begin();
			getSession().save(location);
			commit();
			status = true;
		}catch(HibernateException exc) {
			exc.printStackTrace();
			rollback();
		} finally {
			close();
		}
		return status;
	}
	
	public List<Location> getLocations(){
		List<Location> locations = null;
    	try {
            begin();
            Query q = getSession().createQuery("from Location");
            locations = q.list();
            commit();
        } catch (HibernateException exc) {
        	exc.printStackTrace();
            rollback();
        } finally {
			close();
		}
    	return null;
	}
	
	public boolean removeUser(short userId, short Id){
		boolean status = false;
    	try {
            begin();
            User user = getSession().get(User.class, userId);
            User profile = getSession().get(User.class, Id);
            user.getLocation().getUsers().remove(profile);
            commit();
            status = true;
        } catch (HibernateException exc) {
        	exc.printStackTrace();
            rollback();
        } finally {
			close();
		}
    	return status;
	}
	
	public boolean delete(String street, String city, String country){
		boolean status = false;
    	try {
            begin();
            Query q = getSession().createQuery("delete Location where street=:street and city=:city and country=:country");
            q.executeUpdate();
            commit();
            status = true;
        } catch (HibernateException exc) {
        	exc.printStackTrace();
            rollback();
        } finally {
			close();
		}
    	return status;
	}

}
