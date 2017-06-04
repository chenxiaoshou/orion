package com.polaris.manage.model.mysql.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.polaris.manage.model.mysql.BaseMysqlObject;

/**
 * 菜单表
 * 
 * @author John
 *
 */
@Entity
@Table(name = "PMS_MENU")
public class Menu extends BaseMysqlObject {

	private static final long serialVersionUID = 8773879710657400266L;

	private String parentId;

	private String name;

	private String accessUrl;

	private Integer level;

	private String path;

	private Integer orderIndex;

	@Column(name = "NAME", nullable = false, length = 20, columnDefinition = "varchar(20) default '' comment '菜单名'")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PARENT_ID", nullable = false, length = 64, columnDefinition = "varchar(64) default '' comment '父菜单ID'")
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "LEVEL", nullable = false, columnDefinition = "int(2) default 0 comment '菜单级数'")
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(name = "PATH", nullable = false, length = 512, columnDefinition = "varchar(512) default '' comment '父子菜单关系路径'")
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "ACCESS_URL", nullable = false, length = 128, columnDefinition = "varchar(128) default '' comment '访问路径'")
	public String getAccessUrl() {
		return accessUrl;
	}

	public void setAccessUrl(String accessUrl) {
		this.accessUrl = accessUrl;
	}

	@Column(name = "ORDER_INDEX", nullable = false, columnDefinition = "int(10) default 0 comment '菜单排序索引'")
	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

}
