package com.polaris.manage.model.mysql.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.polaris.manage.model.mysql.BaseMysqlObject;
import com.polaris.manage.model.tools.dic.auth.OperationEnum;

/**
 * 功能操作表
 * 
 * @author John
 *
 */
@Entity
@Table(name = "pms_operation")
public class Operation extends BaseMysqlObject {

	private static final long serialVersionUID = 2611276511784562049L;

	private OperationEnum name; // 操作名称（取自OperationType。调用其name()方法得到的值）

	private String urlPrefix; // 拦截的Url前缀

	@Column(name = "name", nullable = false, length = 32, columnDefinition = "varchar(32) default '' comment '操作名称'")
	public OperationEnum getName() {
		return name;
	}

	public void setName(OperationEnum name) {
		this.name = name;
	}

	@Column(name = "url_prefix", nullable = false, length = 32, columnDefinition = "varchar(32) default '' comment '拦截的Url前缀'")
	public String getUrlPrefix() {
		return urlPrefix;
	}

	public void setUrlPrefix(String urlPrefix) {
		this.urlPrefix = urlPrefix;
	}

}
