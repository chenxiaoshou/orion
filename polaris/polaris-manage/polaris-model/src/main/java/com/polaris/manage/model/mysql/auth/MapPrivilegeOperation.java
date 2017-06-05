package com.polaris.manage.model.mysql.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.polaris.manage.model.mysql.BaseMysqlObject;

/**
 * 权限功能操作映射表
 * 
 * @author John
 *
 */
@Entity
@Table(name = "pms_map_privilege_menu")
public class MapPrivilegeOperation extends BaseMysqlObject {

	private static final long serialVersionUID = -1046830927806702814L;

	private String privilegeId;

	private String operationId;

	@Column(name = "privilege_id", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '外键-权限ID'")
	public String getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}

	@Column(name = "operation_id", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '外键-功能操作ID'")
	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

}
