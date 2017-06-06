package com.polaris.manage.web.vo.auth;

import java.io.Serializable;

public class AuthInfo implements Serializable {
	
	private static final long serialVersionUID = 2986080279980045525L;

	private String username;
	
	private Boolean success;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

}
