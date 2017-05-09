package com.polaris.common.formatter;

import java.lang.reflect.Array;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.polaris.common.constant.SymbolicConstants;
import com.polaris.common.utils.ReflectionUtils;

/**
 * 格式化数组
 * 
 * @author dong
 *
 */
public class ArrayFormatter implements IFormatter {

	private final static Logger LOGGER = LogManager.getLogger(ArrayFormatter.class);
	
	private static final BeanFormatter beanFormatter = new BeanFormatter();

	@Override
	public String toString(Object obj) {
		if (obj == null) {
			return "";
		}
		if (!ReflectionUtils.isArray(obj.getClass())) {
			String msg = "[" + obj.getClass().getName() + "] is not a array";
			LOGGER.error(msg);
			throw new RuntimeException(msg);
		}
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
				sb.append(toString(subObj));
			} else if (clazz.isEnum()) { // 枚举类型
				sb.append(subObj);
			} else { // 其他一般引用类型
				sb.append(beanFormatter.toString(subObj));
			}
			if (i < length - 1) {
				sb.append(SymbolicConstants.HALF_WIDTH_COMMA).append(SymbolicConstants.HALF_WIDTH_BLANK);
			}
		}
		sb.append(SymbolicConstants.RIGHT_BRACKETS);
		return sb.toString();
	}

	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return null;
	}

}
