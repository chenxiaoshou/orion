package com.polaris.common.formatter;

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
	public String format(Object obj) {
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
			strBuf.append(arrayFormatter.format(obj));
		} else if (ReflectionUtils.isCollection(clazz)) { // 集合类型
			strBuf.append(collectionFormatter.format(obj));
		} else if (clazz.isEnum()) { // 枚举类型
			
		} else if (clazz.isAnnotation()) { // 注解
			
		} else if (clazz.isAnonymousClass()) { // 匿名内部类
			
		} else if (clazz.isInterface()) { // 接口
			
		}  else { // 其他一般引用类型
			// TODO
		}
		return strBuf.toString();
	}

}
