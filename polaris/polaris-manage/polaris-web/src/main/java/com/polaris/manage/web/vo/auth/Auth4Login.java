package com.polaris.manage.web.vo.auth;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 传入用户登录认证信息
 * 
 * @author John
 *
 */
public class Auth4Login implements Serializable {

	private static final long serialVersionUID = -7825879421230567483L;

	@NotBlank(message = "auth.username.not_blank")
	private String username;

	@NotBlank(message = "auth.password.not_blank")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
