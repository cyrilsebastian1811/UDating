package com.neu.edu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "LOGIN")
@Inheritance(strategy = InheritanceType.JOINED)
public class Login {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LOGIN_ID")
	private short id;
	
	@Column(name = "ACCOUNT_ID", unique=true, nullable = false, length = 40)
	private String accountId;
	
	@Column(name = "PASSWORD", nullable = false, length = 80)
	private String password;
	
	@Column(name = "ROLE", nullable = false, length = 20)
	private char role;
	
	@Column(name = "EMAIL_ID", unique = true, nullable = false, length = 40)
	private String emailId;
	public String getEmailId() { return emailId; }
	public void setEmailId(String emailId) { this.emailId = emailId; }
	
	
	public Login() {}
	public Login(String accountId, String password, String emailId, char role) {
		this.accountId = accountId;
		this.password = password;
		this.emailId = emailId;
		this.role = role;
	}

	public short getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public char getRole() {
		return role;
	}

	public void setRole(char role) {
		this.role = role;
	}
}
