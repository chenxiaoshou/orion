package com.polaris.manage.model.mysql.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.polaris.manage.model.mysql.BaseMysqlObject;

@Entity
@Table(name = "PMS_MAP_USER_ROLE")
public class MapUserRole extends BaseMysqlObject {

	private static final long serialVersionUID = 7623237985920744323L;

	private String userId;

	private String roleId;

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
