package com.polaris.common.formatter;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Date;

import com.polaris.common.constant.PatternConstants;
import com.polaris.common.constant.SymbolicConstants;
import com.polaris.common.utils.DateUtil;
import com.polaris.common.utils.ReflectionUtils;

/**
 * 格式化java bean，利用反射原理将简单的bean对象转换成String
 * 
 * @author dong
 *
 */
public class BeanFormatter implements IFormatter {

	private static final ArrayFormatter arrayFormatter = new ArrayFormatter();

	private static final CollectionFormatter collectionFormatter = new CollectionFormatter();

	@Override
	public String toString(Object obj) {
		if (obj == null) {
			return "";
		}
		StringBuffer strBuf = new StringBuffer();
		Class<?> clazz = obj.getClass();
		if (ReflectionUtils.isBaseClass(clazz)) { // 基本类型或者基本类型的包装类
			strBuf.append(obj);
		} else if (ReflectionUtils.isString(clazz)) { // String类型
			strBuf.append(obj);
		} else if (clazz.isArray()) { // 数组类型
			strBuf.append(arrayFormatter.toString(obj));
		} else if (ReflectionUtils.isCollection(clazz)) { // 集合类型
			strBuf.append(collectionFormatter.toString(obj));
		} else if (clazz.isEnum()) { // 枚举类型
			strBuf.append(obj);
		} else { // 其他一般引用类型
			Field[] fields = clazz.getDeclaredFields();
			AccessibleObject.setAccessible(fields, true);
			strBuf.append(SymbolicConstants.LEFT_BRACES);
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				if (fields[i].getName().endsWith("serialVersionUID")) { // 过滤掉序列化编号
					continue;
				}
				strBuf.append(field.getName() + SymbolicConstants.EQUAL);
				try {
					if (ReflectionUtils.isBaseClass(field.getType())) { // 基本类型及其包装类
						strBuf.append(field.get(obj));
					} else if (ReflectionUtils.isTimestamp(field.getType())) { // 时间戳
						Timestamp timestamp = (Timestamp) field.get(obj);
						strBuf.append(timestamp == null ? null
								: DateUtil.date2str(new Date(timestamp.getTime()),
										PatternConstants.DATE_FORMAT_PATTERN_1));
					} else if (ReflectionUtils.isDate(field.getType())) { // java.util.Date类型
						Date date = (Date) field.get(obj);
						strBuf.append(
								date == null ? null : DateUtil.date2str(date, PatternConstants.DATE_FORMAT_PATTERN_1));
					} else { // 其他一般引用类型
						strBuf.append(toString(field.get(obj)));
					}
				} catch (Exception e) {
					throw new RuntimeException("格式化 class[" + clazz.getName() + "] field [" + field.getName()
							+ "] 时出现异常 [" + e.getMessage() + "]", e);
				}
				if (i != fields.length - 1) {
					strBuf.append(SymbolicConstants.HALF_WIDTH_COMMA).append(SymbolicConstants.HALF_WIDTH_BLANK);
				}
			}
			strBuf.append(SymbolicConstants.RIGHT_BRACES);
		}
		return strBuf.toString();
	}

	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return null;
	}

}
