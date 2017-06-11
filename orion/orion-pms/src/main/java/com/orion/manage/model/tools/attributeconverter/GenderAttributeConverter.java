package com.orion.manage.model.tools.attributeconverter;

import javax.persistence.AttributeConverter;

import com.orion.manage.model.tools.dic.order.GenderEnum;

public class GenderAttributeConverter implements AttributeConverter<GenderEnum, String> {

	@Override
	public String convertToDatabaseColumn(GenderEnum attribute) {
		return attribute.name();
	}

	@Override
	public GenderEnum convertToEntityAttribute(String dbData) {
		return GenderEnum.valueOf(dbData);
	}

}
