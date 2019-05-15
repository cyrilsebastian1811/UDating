package com.neu.edu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "ADVERT")
public class Advert {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ADVERT_ID")
	private short id;
	
	@Transient
	private MultipartFile photo;
	
	@Column(name = "ADVERT_FILE_NAME")
	private String advertFileName;

	public short getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}

	public MultipartFile getPhoto() {
		return photo;
	}

	public void setPhoto(MultipartFile photo) {
		this.photo = photo;
	}

	public String getAdvertFileName() {
		return advertFileName;
	}

	public void setAdvertFileName(String advertFileName) {
		this.advertFileName = advertFileName;
	}
}
