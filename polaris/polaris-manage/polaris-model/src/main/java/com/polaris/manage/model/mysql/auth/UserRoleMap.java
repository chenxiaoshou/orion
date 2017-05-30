package com.polaris.manage.model.mysql.auth;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.polaris.common.base.BaseObject;

@Entity
@Table(name = "PMS_USER_ROLE_MAP")
public class UserRoleMap extends BaseObject implements Serializable {

	private static final long serialVersionUID = 7623237985920744323L;

	private String id;

	private String userId;

	private String roleId;

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "com.polaris.common.utils.IdGenerator", parameters = {
			@Parameter(name = "idLength", value = "15"), @Parameter(name = "perfix", value = "USER") })
	@Column(name = "ID", nullable = false, columnDefinition = "varchar(64) default '' comment '用户角色关联表唯一标识'")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "USER_ID", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '用户ID'")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "ROLE_ID", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '角色ID'")
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
