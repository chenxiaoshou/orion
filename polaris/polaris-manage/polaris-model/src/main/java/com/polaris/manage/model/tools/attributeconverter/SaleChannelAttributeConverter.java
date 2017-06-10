package com.polaris.manage.model.tools.attributeconverter;

import javax.persistence.AttributeConverter;

import com.polaris.manage.model.tools.dic.order.SaleChannelEnum;

/**
 * 实现数据库与实体类之间saleChannelEnum枚举类型的转换
 * 
 * @author John
 *
 */
public enum SaleChannelAttributeConverter implements AttributeConverter<SaleChannelEnum, String> {

	INSTANCE;

	@Override
	public String convertToDatabaseColumn(SaleChannelEnum attribute) {
		return attribute.getCode();
	}

	@Override
	public SaleChannelEnum convertToEntityAttribute(String dbData) {
		return SaleChannelEnum.valueOf(dbData);
	}

}
