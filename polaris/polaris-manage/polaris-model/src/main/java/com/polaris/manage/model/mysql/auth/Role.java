package com.polaris.manage.model.mysql.auth;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.polaris.common.base.BaseObject;

@Entity
@Table(name = "PMS_ROLE")
public class Role extends BaseObject implements Serializable {

	private static final long serialVersionUID = -6640269699963057898L;

	private String id;

	private String name;

	private boolean enable;

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "com.polaris.common.utils.IdGenerator", parameters = {
			@Parameter(name = "idLength", value = "15"), @Parameter(name = "perfix", value = "USER") })
	@Column(name = "ID", nullable = false, columnDefinition = "varchar(128) default '' comment '角色唯一标识'")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "ENABLE", nullable = false, columnDefinition = "bit(1) default 0 comment '是否启用'")
	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
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
