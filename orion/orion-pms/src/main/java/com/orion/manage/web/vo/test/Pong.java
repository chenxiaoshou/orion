package com.orion.manage.web.vo.test;

import java.io.Serializable;

public class Pong implements Serializable {

	private static final long serialVersionUID = 7451674139876680371L;
	
	private String ack;
	
	private String message;

	public String getAck() {
		return ack;
	}

	public void setAck(String ack) {
		this.ack = ack;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
