package com.polaris.manage.model.solr;

import java.sql.Timestamp;

import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.Id;

import com.polaris.common.base.BaseObject;

@MappedSuperclass
public class BaseSolrObject extends BaseObject {

	private static final long serialVersionUID = -8527624880875821522L;

	protected String id;

	protected String creator; // 创建者

	protected Timestamp createTime; // 创建时间

	protected String updater; // 更新者

	protected Timestamp updateTime; // 更新时间

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}
