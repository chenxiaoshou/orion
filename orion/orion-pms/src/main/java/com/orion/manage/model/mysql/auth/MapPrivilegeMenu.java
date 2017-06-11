package com.orion.manage.model.mysql.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.orion.manage.model.mysql.BaseMysqlObject;

/**
 * 权限菜单映射表
 * 
 * @author John
 *
 */
@Entity
@Table(name = "pms_map_privilege_menu")
public class MapPrivilegeMenu extends BaseMysqlObject {

	private static final long serialVersionUID = -5373816674121902181L;

	private String menuId;

	private String privilegeId;

	@Column(name = "menu_id", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '外键-菜单ID'")
	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	@Column(name = "privilege_id", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '外键-权限ID'")
	public String getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}

}
