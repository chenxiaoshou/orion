package com.polaris.common.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

/**
 * 
 * Json与Object之间相互转换的工具方法，使用jackson
 *
 */
public final class JsonUtil<T> {

	private final Class<T> beanClass;

	private final Class<?>[] elementClasses;

	private JsonUtil(Class<T> beanClass, Class<?>... elementClasses) {
		this.beanClass = beanClass;
		this.elementClasses = elementClasses;
	}

	public static final String DATE_FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";

	public static <T> JsonUtil<T> binder(Class<T> beanClass, Class<?>... elementClasses) {
		return new JsonUtil<T>(beanClass, elementClasses);
	}

	public static ObjectMapper createMapper() {
		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_DATETIME);
		mapper.setDateFormat(dateFormat); // 设置jackson时间格式化的格式
		// Doing so you can now use both JAXB annotation and Jackson native
		// annotation.
		JaxbAnnotationModule module = new JaxbAnnotationModule();
		mapper.registerModule(module);
		// 另一种方式配置jaxb注解
		// mapper.setAnnotationIntrospector(new
		// JaxbAnnotationIntrospector(mapper.getTypeFactory()));

		return mapper;
	}

	public T fromJSON(String json) throws Exception {
		ObjectMapper mapper = createMapper();
		try {
			return elementClasses == null || elementClasses.length == 0 ? mapper.readValue(json, beanClass)
					: fromJSONToGeneric(json);
		} catch (Exception e) {
			throw e;
		}
	}

	private T fromJSONToGeneric(String json) throws IOException {
		ObjectMapper mapper = createMapper();
		return mapper.readValue(json, mapper.getTypeFactory().constructParametricType(beanClass, elementClasses));
	}

	public String toJSON(T object) throws Exception {
		ObjectMapper mapper = createMapper();
		try {
			return mapper.writeValueAsString(object);
		} catch (Exception e) {
			throw e;
		}
	}
}