package com.neu.edu.model;

public class DisplayUser {
	private short id;
	private String fullName;
	private short age;
	private char sex;
	
	public short getId() {
		return id;
	}
	public void setId(short id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public short getAge() {
		return age;
	}
	public void setAge(short age) {
		this.age = age;
	}
	public char getSex() {
		return sex;
	}
	public void setSex(char sex) {
		this.sex = sex;
	}
}
