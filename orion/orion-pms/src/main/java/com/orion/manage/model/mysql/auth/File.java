package com.orion.manage.model.mysql.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.orion.manage.model.mysql.BaseMysqlObject;

/**
 * 文件表
 * 
 * @author John
 *
 */
@Entity
@Table(name = "pms_file")
public class File extends BaseMysqlObject {

	private static final long serialVersionUID = 8867563744221935474L;
	
	private String name; // 文件名
	
	private String path; // 文件路径

	@Column(name = "name", nullable = false, length = 32, columnDefinition = "varchar(32) default '' comment '文件名'")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "path", nullable = false, length = 255, columnDefinition = "varchar(255) default '' comment '文件路径'")
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
