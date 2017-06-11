package com.orion.manage.model.tools.dic.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderStatusEnum {

	STEP_AWAIT_PROCESSING(1), // 待处理
	STEP_AWAIT_SHIPPING(2), // 待发货
	STEP_SHIPPING_FINISHED(3), // 已发货
	STEP_COMPLETED_ORDER(4), // 已发货
	STEP_ISSUE_ORDER(5); // 问题订单

	private int status;

	private OrderStatusEnum(int status) {
		this.status = status;
	}

	@JsonValue // Json序列化时，显示的是自定义的status值，并不是name()方法之后的值
	public int getStatus() {
		return status;
	}

	@JsonCreator // Json反序列化
	public static OrderStatusEnum getOrderStatusByStatus(int status) {
		for (OrderStatusEnum orderStatus : OrderStatusEnum.values()) {
			if (orderStatus.getStatus() == status) {
				return orderStatus;
			}
		}
		return null;
	}

}
