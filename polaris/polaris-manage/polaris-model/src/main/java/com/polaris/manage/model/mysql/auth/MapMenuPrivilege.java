package com.polaris.manage.model.mysql.auth;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 菜单权限映射表
 * @author John
 *
 */
@Entity
@Table(name = "PMS_MAP_MENU_PRIVILEGE")
public class MapMenuPrivilege implements Serializable {

	private static final long serialVersionUID = -5373816674121902181L;
	
	private String id;
	
	private String menuId;
	
	private String privilegeId;

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@Column(name = "ID", nullable = false, columnDefinition = "varchar(64) default '' comment '主键唯一标识'")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "MENU_ID", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '外键-菜单ID'")
	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	@Column(name = "PRIVILEGE_ID", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '外键-权限ID'")
	public String getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}
	
}
