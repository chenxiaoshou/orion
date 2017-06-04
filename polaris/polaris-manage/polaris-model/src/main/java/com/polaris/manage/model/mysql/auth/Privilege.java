package com.polaris.manage.model.mysql.auth;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 权限表
 * @author John
 *
 */
@Entity
@Table(name = "PMS_PRIVILEGE")
public class Privilege implements Serializable {

	private static final long serialVersionUID = 5240662074365174587L;

	private String id;
	
	private String type; // 权限类型

	private String desc; // 权限描述
	
	private String creator; // 创建者

	private Timestamp createTime; // 创建时间

	private String updater; // 更新者

	private Timestamp updateTime; // 更新时间
	
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
	
	@Column(name = "TYPE", nullable = false, length = 16, columnDefinition = "varchar(16) default '' comment '权限类型(add,delete,view,modify,execute等)'")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Column(name = "CREATOR", nullable = false, columnDefinition = "varchar(64) default '' comment '创建者ID'")
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "CREATE_TIME", nullable = false, updatable = false, columnDefinition = "DATETIME default CURRENT_TIMESTAMP comment '创建时间'")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATER", nullable = false, columnDefinition = "varchar(64) default '' comment '更新者ID'")
	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	@Column(name = "UPDATE_TIME", nullable = true, columnDefinition = "DATETIME default NULL comment '更新时间'")
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "DESC", nullable = false, length = 255, columnDefinition = "varchar(255) default '' comment '权限描述'")
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
