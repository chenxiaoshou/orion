package com.orion.manage.model.tools.dic.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderStatusEnum {

	STEP_AWAIT_PROCESSING("step_await_processing", "待处理"), 
	STEP_AWAIT_SHIPPING("step_await_shipping","待发货"), 
	STEP_SHIPPING_FINISHED("step_shipping_finished", "已发货"), 
	STEP_COMPLETED_ORDER("step_completed_order", "已完成"), 
	STEP_ISSUE_ORDER("step_issue_order", "问题订单");

	private String status;

	private String desc;

	private OrderStatusEnum(String status, String desc) {
		this.status = status;
		this.desc = desc;
	}

	@JsonValue // Json序列化时，显示的是自定义的status值，并不是name()方法之后的值
	public String getStatus() {
		return this.status;
	}

	public String getDesc() {
		return this.desc;
	}

	@JsonCreator // Json反序列化
	public static OrderStatusEnum getOrderStatusByStatus(String status) {
		for (OrderStatusEnum orderStatus : OrderStatusEnum.values()) {
			if (orderStatus.getStatus().equalsIgnoreCase(status)) {
				return orderStatus;
			}
		}
		return null;
	}

}
