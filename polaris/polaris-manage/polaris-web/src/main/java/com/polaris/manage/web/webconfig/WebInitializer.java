package com.polaris.manage.web.webconfig;

import javax.servlet.Filter;

import org.springframework.core.annotation.Order;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.polaris.common.config.ApplicationConfig;
import com.polaris.common.constant.PolarisConstants;

@Order(2)
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

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding(PolarisConstants.CHAESET_UTF_8);
		characterEncodingFilter.setForceEncoding(true);
		return new Filter[] { characterEncodingFilter };
	}

}
