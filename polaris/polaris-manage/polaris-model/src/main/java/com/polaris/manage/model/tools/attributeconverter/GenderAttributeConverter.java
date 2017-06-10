package com.polaris.manage.model.tools.attributeconverter;

import javax.persistence.AttributeConverter;

import com.polaris.manage.model.tools.dic.order.GenderEnum;

public enum GenderAttributeConverter implements AttributeConverter<GenderEnum, String> {

	INSTANCE;

	@Override
	public String convertToDatabaseColumn(GenderEnum attribute) {
		return attribute.name();
	}

	@Override
	public GenderEnum convertToEntityAttribute(String dbData) {
		return GenderEnum.valueOf(dbData);
	}

}
