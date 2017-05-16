package com.polaris.common.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;

public class SpringUtil {

	private static final Logger LOGGER = LogManager.getLogger(SpringUtil.class);
	
	private ApplicationContext applicationContext;

	private SpringUtil() {
	}

	private static class SingletonHolder {
		private SingletonHolder(){}
		private static SpringUtil beanUtil = new SpringUtil();
	}

	public static SpringUtil getInstance() {
		return SingletonHolder.beanUtil;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	/**
	 * 容器启动时，会将applicationContext注入到当前类中。如果是Junit测试，需要手动注入才能使用
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName) {
		ApplicationContext ac = SpringUtil.getInstance().getApplicationContext();
		if (ac == null) {
			LOGGER.error("failed inject applicationContext into SpringUtil");
			throw new RuntimeException("Failed inject applicationContext into SpringUtil");
		}
		return ac.getBean(beanName);
	}
	
}
