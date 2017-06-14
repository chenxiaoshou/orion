package com.orion.manage.web.vo.auth;

import javax.validation.constraints.NotNull;

import com.orion.common.base.BaseObject;
import com.orion.manage.model.tools.dic.order.GenderEnum;

public class Auth4Register extends BaseObject {

	private static final long serialVersionUID = -8769381805327995958L;
	
	@NotNull(message = "auth.username.is_null")
	private String username; // 登录用户名

	@NotNull(message = "auth.password.is_null")
	private String password; // 加密之后的密码(加密算法：BCrypt)

	@NotNull(message = "auth.realname.is_null")
	private String realName; // 员工姓名

	private GenderEnum gender; // 性别

	@NotNull(message = "auth.roles.is_null")
	private String roles; // role角色字符串拼接，冗余字段

	@NotNull(message = "auth.mobile.is_null")
	private String mobile;

	private String idCard;

	@NotNull(message = "auth.email.is_null")
	private String email;

	private Boolean enable;

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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public GenderEnum getGender() {
		return gender;
	}

	public void setGender(GenderEnum gender) {
		this.gender = gender;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
	
}
