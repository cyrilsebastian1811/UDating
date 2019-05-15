package com.neu.edu.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MESSAGE")
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MESSAGE_ID")
	private short id;
	
	@Column(name = "FROM_ID")
	private short fromId;
	
	@Column(name = "TO_ID")
	private short toId;
	
	@Column(name = "MSG")
	private String msg;
	
	
	public Message(){
	}
	
	public Message(short fromId, short toId, String msg){
		this.fromId = fromId;
		this.toId = toId;
		this.msg = msg;
	}

	public short getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}

	public short getFromId() {
		return fromId;
	}

	public void setFromId(short fromId) {
		this.fromId = fromId;
	}

	public short getToId() {
		return toId;
	}

	public void setToId(short toId) {
		this.toId = toId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
