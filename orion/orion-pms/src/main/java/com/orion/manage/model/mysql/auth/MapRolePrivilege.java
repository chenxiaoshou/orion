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

	public MapRolePrivilege(String roleId, String privilegeId) {
		super();
		this.roleId = roleId;
		this.privilegeId = privilegeId;
	}

	public MapRolePrivilege() {
		super();
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((privilegeId == null) ? 0 : privilegeId.hashCode());
		result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MapRolePrivilege other = (MapRolePrivilege) obj;
		if (privilegeId == null) {
			if (other.privilegeId != null)
				return false;
		} else if (!privilegeId.equals(other.privilegeId))
			return false;
		if (roleId == null) {
			if (other.roleId != null)
				return false;
		} else if (!roleId.equals(other.roleId))
			return false;
		return true;
	}

}
