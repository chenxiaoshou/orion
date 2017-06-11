package com.orion.manage.model.mysql.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.orion.manage.model.mysql.BaseMysqlObject;

@Entity
@Table(name = "pms_map_role_privilege")
public class MapRolePrivilege extends BaseMysqlObject {

	private static final long serialVersionUID = 7155989115529071627L;

	private String roleId;

	private String privilegeId;

	@Column(name = "role_id", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '外键-角色ID'")
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name = "privilege_id", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '外键-权限ID'")
	public String getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}

}
