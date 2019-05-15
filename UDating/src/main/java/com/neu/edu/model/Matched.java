package com.neu.edu.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "MATCHED")
public class Matched {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MATCHED_ID")
	private short id;
	
	@ManyToMany
	@JoinTable(
			name = "USER_MATCHED",
			joinColumns = @JoinColumn(name = "FK_MATCHED_ID"),
			inverseJoinColumns = @JoinColumn(name = "FK_USER_ID")
	)
	private Set<User> users = new HashSet<User>();

	public short getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

}
