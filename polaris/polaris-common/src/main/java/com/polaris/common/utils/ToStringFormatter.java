package com.polaris.common.utils;

import java.lang.reflect.Array;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.polaris.common.constant.SymbolicConstants;

import net.sf.json.JSONObject;

/**
 * 格式化数组
 * 
 * @author dong
 *
 */
public final class ToStringFormatter {

	private ToStringFormatter() {

	}

	/**
	 * 输出类toString方法格式的字符串
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		String result = "";
		Class<?> clz = obj.getClass();
		if (ReflectionUtils.isArray(clz)) {
			result = arrayToString(obj, ToStringStyle.SHORT_PREFIX_STYLE);
		} else {
			result = ToStringBuilder.reflectionToString(obj, ToStringStyle.SHORT_PREFIX_STYLE);
		}
		return result;
	}

	/**
	 * 输出Json风格的字符串
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		String result = "";
		Class<?> clz = obj.getClass();
		String typeName = obj.getClass().getSimpleName();
		if (ReflectionUtils.isArray(clz)) {
			typeName = typeName.replaceAll("\\[\\]", "");
			result = arrayToString(obj, ToStringStyle.JSON_STYLE);
		} else {
			result = ToStringBuilder.reflectionToString(obj, ToStringStyle.JSON_STYLE);
		}
		JSONObject json = new JSONObject();
		json.accumulate(typeName, result);
		return json.toString();
	}

	private static String arrayToString(Object obj, ToStringStyle style) {
		StringBuffer sb = new StringBuffer("");
		int length = Array.getLength(obj);
		sb.append(SymbolicConstants.LEFT_BRACKETS);
		for (int i = 0; i < length; i++) {
			Object subObj = Array.get(obj, i);
			subObj = subObj == null ? "" : subObj;
			Class<?> clazz = subObj.getClass();
			if (ReflectionUtils.isBaseClass(clazz) || ReflectionUtils.isString(clazz)) { // 基本类型
				sb.append(String.valueOf(subObj));
			} else if (clazz.isArray()) { // 数组
				sb.append(arrayToString(subObj, style));
			} else { // 其他一般引用类型
				sb.append(ToStringBuilder.reflectionToString(subObj, style));
			}
			if (i < length - 1) {
				sb.append(SymbolicConstants.HALF_WIDTH_COMMA).append(SymbolicConstants.HALF_WIDTH_BLANK);
			}
		}
		sb.append(SymbolicConstants.RIGHT_BRACKETS);
		return sb.toString();
	}

}
