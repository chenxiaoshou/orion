package com.polaris.manage.model.mysql.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.polaris.manage.model.mysql.BaseMysqlObject;

@Entity
@Table(name = "PMS_MAP_ROLE_PRIVILEGE")
public class MapRolePrivilege extends BaseMysqlObject {

	private static final long serialVersionUID = 7155989115529071627L;

	private String roleId;

	private String privilegeId;

	@Column(name = "ROLE_ID", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '外键-角色ID'")
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name = "PRIVILEGE_ID", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '外键-权限ID'")
	public String getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}

}
