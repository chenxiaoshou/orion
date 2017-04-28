package com.polaris.common.formatter;

import java.lang.reflect.Array;

import com.polaris.common.constant.SymbolicConstants;
import com.polaris.common.utils.ReflectionUtils;

/**
 * 格式化数组
 * 
 * @author dong
 *
 */
public class ArrayFormatter implements IFormatter {
	
	private static final BeanFormatter beanFormatter = new BeanFormatter();

	@Override
	public String format(Object obj) {
		if (obj == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer("");
		int length = Array.getLength(obj);
		for (int i = 0; i < length; i++) {
			if (i == 0) {
				sb.append(SymbolicConstants.LEFT_BRACKETS);
			} else {
				sb.append(SymbolicConstants.HALF_WIDTH_COMMA).append(SymbolicConstants.HALF_WIDTH_BLANK);
			}
			Object subObj = Array.get(obj, i);
			subObj = subObj == null ? "" : subObj;
			Class<?> clazz = subObj.getClass();
			if (clazz.isPrimitive() || clazz.isInstance(String.class) || ReflectionUtils.isBaseClass(clazz)) { // 基本类型
				sb.append(String.valueOf(subObj));
			} else if (clazz.isArray()) { // 数组 
				sb.append(format(subObj));
			} else if (clazz.isEnum()) { // 枚举类型
				
			} else if (clazz.isAnnotation()) { // 注解
				
			} else if (clazz.isAnonymousClass()) { // 匿名内部类
				
			} else if (clazz.isInterface()) { // 接口
				
			} else { // 其他一般引用类型
				sb.append(beanFormatter.format(subObj));
			}
			if (i == length - 1) {
				sb.append(SymbolicConstants.RIGHT_BRACKETS);
			}
		}
		return sb.toString();
	}
	
}
