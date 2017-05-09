package com.polaris.common.formatter;

import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.polaris.common.constant.SymbolicConstants;
import com.polaris.common.utils.ReflectionUtils;

/**
 * 格式化集合
 * 
 * @author John
 *
 */
public class CollectionFormatter implements IFormatter {

	private final static Logger LOGGER = LogManager.getLogger(CollectionFormatter.class);

	private static final BeanFormatter beanFormatter = new BeanFormatter();

	@Override
	public String toString(Object obj) {
		if (obj == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		if (!ReflectionUtils.isCollection(obj.getClass())) {
			String msg = "[" + obj.getClass().getName() + "] is not a collection";
			LOGGER.error(msg);
			throw new RuntimeException(msg);
		}
		Collection<?> coll = (Collection<?>) obj;
		int i = 0, size = coll.size();
		sb.append(SymbolicConstants.LEFT_BRACKETS);
		for (Object innerObj : coll) {
			innerObj = innerObj == null ? "" : innerObj;
			Class<?> innerClz = innerObj.getClass();
			if (ReflectionUtils.isBaseClass(innerClz) || ReflectionUtils.isString(innerClz)) { // 基本类型
				sb.append(String.valueOf(innerObj));
			} else if (innerClz.isArray()) { // 数组
				sb.append(toString(innerObj));
			} else if (innerClz.isEnum()) { // 枚举类型
				sb.append(innerObj);
			} else { // 其他一般引用类型
				sb.append(beanFormatter.toString(innerObj));
			}
			if (i++ < size - 1) {
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
