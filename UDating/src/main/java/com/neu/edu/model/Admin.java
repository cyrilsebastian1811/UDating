package com.neu.edu.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TINDER_ADMIN")
public class Admin extends Login{
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_ADVERTS")
	private Set<Advert> adverts = new HashSet<Advert>();
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_USERS")
	private Set<User> users = new HashSet<User>();

	public Set<Advert> getAdverts() {
		return adverts;
	}

	public void setAdverts(Set<Advert> adverts) {
		this.adverts = adverts;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	
}
