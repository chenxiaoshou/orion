package com.polaris.manage.persist.mysql.order.dto;

import com.polaris.common.base.BaseCriteria;

public class SearchOrderCriteria extends BaseCriteria {

	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
