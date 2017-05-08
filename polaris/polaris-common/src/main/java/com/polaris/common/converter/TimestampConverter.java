package com.polaris.common.converter;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/**
 * Timestamp转换器 将Date转换成Timestamp
 * 
 * @ClassName: TimestampConverter
 * @author John
 *
 */
public enum TimestampConverter implements Converter<Date, Timestamp> {

	INSTANCE;
	
	@Override
	public Timestamp convert(Date date) {
		if (date != null) {
			return new Timestamp(date.getTime());
		}
		return null;
	}

}
