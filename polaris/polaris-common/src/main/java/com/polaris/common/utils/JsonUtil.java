package com.polaris.common.utils;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

/**
 * 
 * Json与Object之间相互转换的工具方法，使用jackson
 *
 */
public final class JsonUtil<T> {

	public static final String DATE_FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";

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
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper;
	}

	/**
	 * 
	 * @param json ： json源文件
	 * @param beanClass ： 转换目标类
	 * @param elementClasses ： 复杂对象的转换
	 * @return
	 * @throws Exception
	 */
	public static <T> T fromJSON(String json, Class<T> beanClass, Class<?>... elementClasses) throws Exception {
		ObjectMapper mapper = createMapper();
		try {
			return elementClasses == null || elementClasses.length == 0 ? mapper.readValue(json, beanClass)
					: mapper.readValue(json,
							mapper.getTypeFactory().constructParametricType(beanClass, elementClasses));
		} catch (Exception e) {
			throw e;
		}
	}

	public static <T> String toJSON(T t) throws Exception {
		ObjectMapper mapper = createMapper();
		try {
			return mapper.writeValueAsString(t);
		} catch (Exception e) {
			throw e;
		}
	}

}