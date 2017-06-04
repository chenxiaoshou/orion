package com.polaris.manage.model.mysql.order.dic;

public enum OrderStatus {

	STEP_AWAIT_PROCESSING(1), // 待处理
	STEP_AWAIT_SHIPPING(2), // 待发货
	STEP_SHIPPING_FINISHED(3), // 已发货
	STEP_COMPLETED_ORDER(4), // 已发货
	STEP_ISSUE_ORDER(5); // 问题订单
	
	private int status;
	
	private OrderStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}
	
}
