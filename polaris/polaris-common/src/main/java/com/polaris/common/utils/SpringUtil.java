package com.polaris.common.utils;

import org.springframework.context.ApplicationContext;

public class SpringUtil {

	private static ApplicationContext applicationContext;

	private SpringUtil() {
	}

	private static class SingletonHolder {
		private static SpringUtil beanUtil = new SpringUtil();
	}

	public static SpringUtil getInstance() {
		return SingletonHolder.beanUtil;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringUtil.applicationContext = applicationContext;
	}

	public static Object getBean(String beanName) {
		return SpringUtil.getInstance().getApplicationContext().getBean(beanName);
	}
	
}
