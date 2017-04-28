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

	public static Object getBean(String beanName) {
		return BeanUtil.getInstance().getApplicationContext().getBean(beanName);
	}
	
}
