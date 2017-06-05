package com.polaris.manage.model.mysql.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.polaris.manage.model.mysql.BaseMysqlObject;

/**
 * 角色表
 * 
 * @author John
 *
 */
@Entity
@Table(name = "PMS_ROLE")
public class Role extends BaseMysqlObject {

	private static final long serialVersionUID = -6640269699963057898L;

	private String name;

	private Boolean enable;

	@Column(name = "ENABLE", nullable = false, columnDefinition = "bit(1) default 0 comment '是否启用'")
	public Boolean isEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	@Column(name = "NAME", nullable = false, length = 20, columnDefinition = "varchar(20) default '' comment '角色名'")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
