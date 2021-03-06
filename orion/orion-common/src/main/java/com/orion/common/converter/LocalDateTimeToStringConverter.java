package com.orion.common.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.core.convert.converter.Converter;

import com.orion.common.constant.PatternConstants;

public class LocalDateTimeToStringConverter implements Converter<LocalDateTime, String> {

	@Override
	public String convert(LocalDateTime source) {
		return source.format(DateTimeFormatter.ofPattern(PatternConstants.DATE_FORMAT_PATTERN_1));
	}

}
