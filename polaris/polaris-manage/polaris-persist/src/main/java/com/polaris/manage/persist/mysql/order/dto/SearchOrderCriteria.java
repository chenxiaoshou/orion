package com.polaris.manage.persist.mysql.order.dto;

import com.polaris.common.base.BaseCriteria;

public class SearchOrderCriteria extends BaseCriteria {

	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
