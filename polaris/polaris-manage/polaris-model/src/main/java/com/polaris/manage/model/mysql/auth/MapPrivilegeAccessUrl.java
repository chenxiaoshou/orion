package com.polaris.manage.model.mysql.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.polaris.manage.model.mysql.BaseMysqlObject;

@Entity
@Table(name = "pms_map_privilege_access_url")
public class MapPrivilegeAccessUrl extends BaseMysqlObject {

	private static final long serialVersionUID = 7737715396514997118L;

	private String privilegeId;

	private String accessUrlId;

	@Column(name = "privilege_id", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '外键-权限ID'")
	public String getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}

	@Column(name = "access_url_id", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '外键-可访问url前缀ID'")
	public String getAccessUrlId() {
		return accessUrlId;
	}

	public void setAccessUrlId(String accessUrlId) {
		this.accessUrlId = accessUrlId;
	}

}
