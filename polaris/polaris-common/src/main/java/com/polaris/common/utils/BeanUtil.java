package com.polaris.common.utils;

import org.springframework.context.ApplicationContext;

public class BeanUtil {

	private static ApplicationContext applicationContext;

	private BeanUtil() {
	}

	private static class SingletonHolder {
		private static BeanUtil beanUtil = new BeanUtil();
	}

	public static BeanUtil getInstance() {
		return SingletonHolder.beanUtil;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		BeanUtil.applicationContext = applicationContext;
	}

	/**
	 * 描述 : <获得applicationContext中的对象>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName) {
		return BeanUtil.getInstance().getApplicationContext().getBean(beanName);
	}

	public static void main(String[] args) {
		BeanUtil beanUtil1 = BeanUtil.getInstance();
		BeanUtil beanUtil2 = BeanUtil.getInstance();
		System.out.println(beanUtil1 == beanUtil2);
	}
	
}
