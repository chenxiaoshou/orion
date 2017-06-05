package com.polaris.manage.model.mysql.auth;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.polaris.common.constant.PolarisConstants;
import com.polaris.manage.model.mysql.BaseMysqlObject;

/**
 * 用户表
 * 
 * @author John
 *
 */
@Entity
@Table(name = "pms_user", schema = PolarisConstants.POLARIS_AUTH_DB, indexes = {
		@Index(columnList = "username", name = "idx_username"),
		@Index(columnList = "password", name = "idx_password") })
public class User extends BaseMysqlObject {

	private static final long serialVersionUID = -7906916919904469152L;

	private String username; // 登录用户名

	private String password; // SHA256加密之后的密码

	private String realName; // 员工姓名

	private Integer gender; // 性别

	private String mobile;

	private String idCard;

	private String email;

	private Boolean enable;

	private Boolean locked;
	
	private Timestamp lastLoginTime;
	
	private Timestamp lastPasswordResetTime;

	@Column(name = "username", nullable = false, length = 255, columnDefinition = "varchar(255) default '' comment '用户名'")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", nullable = false, length = 255, columnDefinition = "varchar(255) default '' comment '密码'")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "enable", nullable = false, columnDefinition = "bit(1) default 0 comment '是否启用'")
	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	@Column(name = "mobile", nullable = false, length = 20, columnDefinition = "varchar(20) default '' comment '电话'")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "idcard", nullable = false, length = 32, columnDefinition = "varchar(32) default '' comment '身份证号码'")
	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	@Column(name = "last_login_time", nullable = true, columnDefinition = "DATETIME default NULL comment '最后登录时间'")
	public Timestamp getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	@Column(name = "gender", nullable = false, columnDefinition = "TINYINT(2) default 0 comment '性别(0：不明，1：女性， 2：男性)'")
	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	@Column(name = "realname", nullable = false, length = 16, columnDefinition = "varchar(16) default '' comment '真实姓名'")
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Column(name = "email", nullable = false, length = 32, columnDefinition = "varchar(32) default '' comment '邮箱'")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "last_password_reset_time", nullable = true, columnDefinition = "DATETIME default NULL comment '最后一次密码重置的时间'")
	public Timestamp getLastPasswordResetTime() {
		return lastPasswordResetTime;
	}

	public void setLastPasswordResetTime(Timestamp lastPasswordResetTime) {
		this.lastPasswordResetTime = lastPasswordResetTime;
	}

	@Column(name = "locked", nullable = false, columnDefinition = "bit(1) default 0 comment '是否被锁定'")
	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

}
