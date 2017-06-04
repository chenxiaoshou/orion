package com.polaris.manage.model.mysql.auth;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.polaris.common.base.BaseObject;

/**
 * 角色表
 * 
 * @author John
 *
 */
@Entity
@Table(name = "PMS_ROLE")
public class Role extends BaseObject implements Serializable {

	private static final long serialVersionUID = -6640269699963057898L;

	private String id;

	private String name;

	private Boolean enable;

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
	
}
