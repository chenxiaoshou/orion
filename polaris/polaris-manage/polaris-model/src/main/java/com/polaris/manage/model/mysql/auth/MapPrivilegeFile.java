package com.polaris.manage.model.mysql.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.polaris.manage.model.mysql.BaseMysqlObject;
/**
 * 权限文件映射表
 * @author John
 *
 */
@Entity
@Table(name = "PMS_MAP_PRIVILEGE_FILE")
public class MapPrivilegeFile extends BaseMysqlObject {

	private static final long serialVersionUID = 2456486329644182616L;
	
	private String privilegeId;
	
	private String fileId;

	@Column(name = "PRIVILEGE_ID", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '外键-权限ID'")
	public String getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}

	@Column(name = "FILE_ID", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '外键-文件ID'")
	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

}
