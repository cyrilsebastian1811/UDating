package com.neu.edu.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToOne;

@Entity
@Table(name = "USER")
public class User extends Login{
	
	public User(){
		this.location = new Location();
		this.matched = new Matched();
	}
	
	@Column(name = "FULL_NAME", unique = true, nullable = false, length = 40)
	private String fullName;
	public String getFullName() { return fullName; }
	public void setFullName(String fullName) { this.fullName = fullName; }
	
	@Column(name = "AGE", nullable = false)
	private short age;
	public short getAge() { return age; }
	public void setAge(short age) { this.age = age; }
	
	
	@Column(name = "SEX", nullable = false)
	private char sex;
	public char getSex() { return sex; }
	public void setSex(char sex) { this.sex = sex; }
	
	
	@Column(name = "AGE_PREFERENCE", nullable = false)
	private short agePreference;
	public short getAgePreference() { return agePreference; }
	public void setAgePreference(short agePreference) { this.agePreference = agePreference; }
	
	
	@Column(name = "SEX_PREFERENCE", nullable = false)
	private char sexPreference;
	public char getSexPreference() { return sexPreference; }
	public void setSexPreference(char sexPreference) { this.sexPreference = sexPreference; }
	
	
	@Column(name = "BIO")
	private String bio;
	public String getBio() { return bio; }
	public void setBio(String bio) { this.bio = bio; }
	

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_LOCATION_ID", nullable = false)
	private Location location;
	public Location getLocation() { return location; }
	public void setLocation(Location location) { this.location = location; }

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_MATCHED_ID", nullable = false)
	private Matched matched;
	public Matched getMatched() { return matched; }
	public void setMatched(Matched matched) { this.matched = matched; }
	
	
	@Column(name = "PHOTO_FILE_PATH")
	private String photoFilePath;
	
	public String getPhotoFilePath() { return photoFilePath; }
	public void setPhotoFilePath(String photoFilePath) { this.photoFilePath = photoFilePath; }
	
	
	////////////////////////////////
	@Transient
	private MultipartFile photo;
	
	public MultipartFile getPhoto() {
		return photo;
	}
	public void setPhoto(MultipartFile photo) {
		this.photo = photo;
	}
	////////////////////////////////
}
