package com.polaris.manage.persist.mysql.order.dto;

import com.polaris.common.base.BaseCriteria;
import com.polaris.manage.model.tools.dic.order.OrderStatusEnum;

public class SearchOrderCriteria extends BaseCriteria {

	private static final long serialVersionUID = 8854836302799106462L;
	
	private OrderStatusEnum status;

	public OrderStatusEnum getStatus() {
		return status;
	}

	public void setStatus(OrderStatusEnum status) {
		this.status = status;
	}

}
