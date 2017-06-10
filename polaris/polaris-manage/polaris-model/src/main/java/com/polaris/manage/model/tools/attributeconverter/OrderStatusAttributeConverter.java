package com.polaris.manage.model.tools.attributeconverter;

import javax.persistence.AttributeConverter;

import com.polaris.manage.model.tools.dic.order.OrderStatusEnum;

public enum OrderStatusAttributeConverter implements AttributeConverter<OrderStatusEnum, Integer> {

	INSTANCE;

	@Override
	public Integer convertToDatabaseColumn(OrderStatusEnum attribute) {
		return attribute.getStatus();
	}

	@Override
	public OrderStatusEnum convertToEntityAttribute(Integer dbData) {
		return OrderStatusEnum.getOrderStatusByStatus(dbData.intValue());
	}

}
