package com.orion.manage.model.tools.attributeconverter;

import javax.persistence.AttributeConverter;

import com.orion.manage.model.tools.dic.order.OrderStatusEnum;

public class OrderStatusAttributeConverter implements AttributeConverter<OrderStatusEnum, String> {

	@Override
	public String convertToDatabaseColumn(OrderStatusEnum attribute) {
		return attribute.getStatus();
	}

	@Override
	public OrderStatusEnum convertToEntityAttribute(String dbData) {
		return OrderStatusEnum.getOrderStatusByStatus(dbData);
	}

}
