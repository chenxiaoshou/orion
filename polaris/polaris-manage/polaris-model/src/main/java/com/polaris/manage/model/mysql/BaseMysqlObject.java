package com.polaris.manage.model.mysql;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.polaris.common.base.BaseObject;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseMysqlObject extends BaseObject {

	private static final long serialVersionUID = 5836536587070114007L;

	protected String id;

	protected String creator; // 创建者

	protected LocalDateTime createTime; // 创建时间

	protected String updater; // 更新者

	protected LocalDateTime updateTime; // 更新时间

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "uuid2")
	@Column(name = "id", nullable = false, unique = true, updatable = false, length = 64, insertable = false, columnDefinition = "varchar(64) default '' comment '主键唯一标识'")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@CreatedBy
	@Column(name = "creator", nullable = false, columnDefinition = "varchar(64) default '' comment '创建者ID'")
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@CreatedDate
	@Column(name = "create_time", nullable = false, updatable = false, columnDefinition = "DATETIME default CURRENT_TIMESTAMP comment '创建时间'")
	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	@LastModifiedBy
	@Column(name = "updater", nullable = false, columnDefinition = "varchar(64) default '' comment '更新者ID'")
	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	@Column(name = "update_time", nullable = true, columnDefinition = "DATETIME default NULL comment '更新时间'")
	@LastModifiedDate
	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

}
