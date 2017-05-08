package com.polaris.common.formatter;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.polaris.common.constant.SymbolicConstants;
import com.polaris.common.utils.ReflectionUtils;

public class MapFormatter implements IFormatter {

	private final static Logger LOGGER = LogManager.getLogger(CollectionFormatter.class);

	private static final BeanFormatter beanFormatter = new BeanFormatter();

	@Override
	public String toString(Object obj) {
		if (obj == null) {
			return "";
		}
		if (!ReflectionUtils.isMap(obj.getClass())) {
			String msg = "[" + obj.getClass().getName() + "] is not a map";
			LOGGER.error(msg);
			throw new RuntimeException(msg);
		}
		StringBuffer sb = new StringBuffer();
		Map<?, ?> map = (Map<?, ?>) obj;
		int i = 0, size = map.size();
		sb.append(SymbolicConstants.LEFT_BRACES);
		for (Map.Entry<?, ?> entry : map.entrySet()) {
			Object key = entry.getKey();
			sb.append(key + SymbolicConstants.EQUAL);
			Object value = entry.getValue();
			Class<?> innerClz = value.getClass();
			if (ReflectionUtils.isBaseClass(innerClz) || ReflectionUtils.isString(innerClz)) { // 基本类型
				sb.append(String.valueOf(value));
			} else if (innerClz.isArray()) { // 数组
				sb.append(toString(value));
			} else if (innerClz.isEnum()) { // 枚举类型
				sb.append(value);
			} else { // 其他一般引用类型
				sb.append(beanFormatter.toString(value));
			}
			if (i++ < size - 1) {
				sb.append(SymbolicConstants.HALF_WIDTH_COMMA).append(SymbolicConstants.HALF_WIDTH_BLANK);
			}
		}
		sb.append(SymbolicConstants.RIGHT_BRACES);
		return sb.toString();
	}

	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return null;
	}

}
