package com.polaris.manage.model.mysql.oa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.polaris.common.base.BaseObject;

@Entity
@Table(name="pms_user", indexes = {
        @Index(columnList = "username", name = "idx_username"),
        @Index(columnList = "password", name = "idx_password")} )
public class User extends BaseObject implements Serializable {

	private static final long serialVersionUID = -7906916919904469152L;
	
	private String id;
	
	private String username;
	
	private String password;

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "com.polaris.common.utils.IdGenerator", parameters = {
			@Parameter(name = "idLength", value = "15"), @Parameter(name = "perfix", value = "USER")})
	@Column(name = "id", nullable = false, columnDefinition = "varchar(128) default '' comment '订单唯一标识'")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "username", nullable = false, length=255, columnDefinition = "varchar(255) default '' comment '用户名'")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", nullable = false, length=255, columnDefinition = "varchar(255) default '' comment '密码'")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
