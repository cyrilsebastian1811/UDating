package com.neu.edu.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "LOCATION")
public class Location {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LOCATION_ID")
	private short id;
	
	@Column(name = "STREET", nullable = false, length = 80)
	private String street;

	@Column(name = "CITY", nullable = false, length = 80)
	private String city;
	
	@Column(name = "COUNTRY", nullable = false, length = 80)
	private String country;
	
	@ManyToMany
	@JoinTable(
			name = "USER_LOCATION",
			joinColumns = @JoinColumn(name = "FK_LOCATION_ID"),
			inverseJoinColumns = @JoinColumn(name = "FK_USER_ID")
	)
	private Set<User> users = new HashSet<User>();
	
	
	public Location() {}
	public Location(String street, String city, String country) {
		this.street = street;
		this.city = city;
		this.country = country;
	}
	

	public short getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street.toLowerCase();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city.toLowerCase();
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country.toLowerCase();
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	@Override
	public boolean equals(Object obj) {
		Location l = (Location)obj;
		if(this.city.equalsIgnoreCase(l.city) && this.country.equalsIgnoreCase(l.country) && this.street.equalsIgnoreCase(l.street)) {
			return true;
		}
		return false;
	}
}
