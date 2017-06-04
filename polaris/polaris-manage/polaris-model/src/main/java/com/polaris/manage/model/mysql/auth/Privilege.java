package com.polaris.manage.model.mysql.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.polaris.manage.model.mysql.BaseMysqlObject;

/**
 * 权限表
 * 
 * @author John
 *
 */
@Entity
@Table(name = "PMS_PRIVILEGE")
public class Privilege extends BaseMysqlObject {

	private static final long serialVersionUID = 5240662074365174587L;

	private String type; // 权限类型

	private String desc; // 权限描述

	@Column(name = "TYPE", nullable = false, length = 16, columnDefinition = "varchar(16) default '' comment '权限类型(add,delete,view,modify,execute等)'")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "DESC", nullable = false, length = 255, columnDefinition = "varchar(255) default '' comment '权限描述'")
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
