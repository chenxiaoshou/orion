package com.polaris.manage.model.mysql.oa;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name="pms_user", indexes = {
        @Index(columnList = "username", name = "idx_username"),
        @Index(columnList = "password", name = "idx_password")} )
public class User implements Serializable {

	private static final long serialVersionUID = -7906916919904469152L;
	
	private String id;
	
	private String username;
	
	private String password;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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
