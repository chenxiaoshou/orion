package com.polaris.manage.model.mysql.auth;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.polaris.common.base.BaseObject;
import com.polaris.common.constant.PolarisConstants;

@Entity
@Table(name = "PMS_USER", schema = PolarisConstants.POLARIS_AUTH_DB, indexes = {
		@Index(columnList = "username", name = "idx_username"),
		@Index(columnList = "password", name = "idx_password") })
public class User extends BaseObject implements Serializable {

	private static final long serialVersionUID = -7906916919904469152L;

	private String id;

	private String username; // 登录用户名

	private String password; // SHA256加密之后的密码
	
	private String realName; // 员工姓名
	
	private int gender; // 性别

	private String mobile;

	private String idCard;

	private String email;
	
	private boolean enable;

	private Timestamp lastLoginTime;

	private String creator;

	private Timestamp createTime;

	private String updater;

	private Timestamp updateTime;

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "com.polaris.common.utils.IdGenerator", parameters = {
			@Parameter(name = "idLength", value = "15"), @Parameter(name = "perfix", value = "USER") })
	@Column(name = "ID", nullable = false, columnDefinition = "varchar(64) default '' comment '用户唯一标识'")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "USERNAME", nullable = false, length = 255, columnDefinition = "varchar(255) default '' comment '用户名'")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "PASSWORD", nullable = false, length = 255, columnDefinition = "varchar(255) default '' comment '密码'")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "ENABLE", nullable = false, columnDefinition = "bit(1) default 0 comment '是否启用'")
	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	@Column(name = "MOBILE", nullable = false, length = 20, columnDefinition = "varchar(20) default '' comment '电话'")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "IDCARD", nullable = false, length = 32, columnDefinition = "varchar(32) default '' comment '身份证号码'")
	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	@Column(name = "LAST_LOGIN_TIME", nullable = true, columnDefinition = "DATETIME default NULL comment '最后登录时间'")
	public Timestamp getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	@Column(name = "CREATOR", nullable = false, columnDefinition = "varchar(64) default '' comment '创建者ID'")
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "CREATE_TIME", nullable = false, updatable = false, columnDefinition = "DATETIME default CURRENT_TIMESTAMP comment '创建时间'")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATER", nullable = false, columnDefinition = "varchar(64) default '' comment '更新者ID'")
	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	@Column(name = "UPDATE_TIME", nullable = true, columnDefinition = "DATETIME default NULL comment '更新时间'")
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "GENDER", nullable = false, columnDefinition = "TINYINT(2) default 0 comment '性别(0：不明，1：女性， 2：男性)'")
	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	@Column(name = "REALNAME", nullable = false, length = 16, columnDefinition = "varchar(16) default '' comment '真实姓名'")
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Column(name = "EMAIL", nullable = false, length = 32, columnDefinition = "varchar(32) default '' comment '邮箱'")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
