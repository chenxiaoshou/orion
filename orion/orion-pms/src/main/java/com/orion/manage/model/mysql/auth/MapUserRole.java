package com.orion.manage.model.mysql.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.orion.manage.model.mysql.BaseMysqlObject;

@Entity
@Table(name = "pms_map_user_role")
public class MapUserRole extends BaseMysqlObject {

	private static final long serialVersionUID = 7623237985920744323L;

	private String userId;

	private String roleId;

	@Column(name = "user_id", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '用户ID'")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "role_id", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '角色ID'")
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
