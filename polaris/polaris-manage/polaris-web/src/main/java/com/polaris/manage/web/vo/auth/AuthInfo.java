package com.polaris.manage.web.vo.auth;

import java.io.Serializable;

import com.polaris.security.util.DeviceEnum;

/**
 * 用户认证成功之后，返回一些必要的信息，供前端使用
 * 
 * @author John
 *
 */
public class AuthInfo implements Serializable {

	private static final long serialVersionUID = 2986080279980045525L;

	private String token;

	private Long createTime; // token创建时间

	private Long expiration; // 过期时间

	private String userId;

	private String username;

	private DeviceEnum device; // 客户端设备

	private String publicKey; // 加密公钥

	private String roles; // 角色拼接字符串

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getExpiration() {
		return expiration;
	}

	public void setExpiration(Long expiration) {
		this.expiration = expiration;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public DeviceEnum getDevice() {
		return device;
	}

	public void setDevice(DeviceEnum device) {
		this.device = device;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

}
