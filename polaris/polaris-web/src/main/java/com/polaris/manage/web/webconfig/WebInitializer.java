package com.polaris.manage.web.webconfig;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.polaris.common.config.ApplicationConfig;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	/**
	 * 配置 Spring 的 org.springframework.web.servlet.DispatcherServlet 的
	 * url-pattern
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	/**
	 * 配置应用的上下文，即所有不包括 SpringMVC 等 Web 配置之外的所有配置， 比如：Spring、Hibernate、AOP 等的配置类
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { ApplicationConfig.class };
	}

	/**
	 * 配置 SpringMVC 等 Web 上下文
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { PolarisServletConfig.class };
	}

}
