package com.polaris.manage.web.vo.auth;

import java.io.Serializable;

/**
 * 用户认证成功之后，返回一些必要的信息，供前端使用
 * 
 * @author John
 *
 */
public class AuthInfo implements Serializable {

	private static final long serialVersionUID = 2986080279980045525L;

	private String token;
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
