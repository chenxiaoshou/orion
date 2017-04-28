package com.polaris.config.web;

import javax.servlet.ServletRegistration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.polaris.common.constant.PolarisConstants;
import com.polaris.common.utils.BeanUtil;
import com.polaris.config.spring.ApplicationConfig;
import com.polaris.config.springmvc.PolarisMvcConfig;

/**
 * Spring MVC DispatcherServlet
 * 
 * @author John
 *
 */
@Order(1)
public class PolarisInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	private static final Logger LOGGER = LogManager.getLogger(PolarisInitializer.class);

	/**
	 * 设置DispatcherServlet的name
	 */
	@Override
	protected String getServletName() {
		return PolarisConstants.DEFAULT_SERVLET_NAME;
	}

	/**
	 * 配置应用的上下文，即所有不包括 SpringMVC 等 Web 配置之外的其他Spring上下文配置，
	 * 比如：Spring、Hibernate、AOP 等的配置类
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		/* return new Class<?>[] { ApplicationConfig.class}; */
		return new Class<?>[] { ApplicationConfig.class };
	}

	/**
	 * 配置 SpringMVC 等 Web 上下文
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { PolarisMvcConfig.class };
	}

	/**
	 * 可以注册DispatcherServlet的初始化参数，等等
	 */
	@Override
	protected void customizeRegistration(ServletRegistration.Dynamic registration) {
		// registration.setInitParameter("spring.profiles.active", "default");
	}

	/**
	 * 配置 Spring 的 org.springframework.web.servlet.DispatcherServlet 的
	 * url-pattern
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[] { PolarisConstants.POLARIS_MAPPING_URL_PATTERN };
	}

	/**
	 * 配置请求路径和DispatcherServlet一致的过滤器, 不一致的可以在WebConfig中配置
	 */
	/*
	 * @Override protected Filter[] getServletFilters() {
	 * CharacterEncodingFilter characterEncodingFilter = new
	 * CharacterEncodingFilter();
	 * characterEncodingFilter.setEncoding(PolarisConstants.CHAESET_UTF_8);
	 * characterEncodingFilter.setForceEncoding(true); return new Filter[] {
	 * characterEncodingFilter }; }
	 */

	/**
	 * 创建一个可以在非spring管理bean中获得spring管理的相关bean的上下文对象: BeanUtil.getBean(beanName);
	 */
	@Override
	protected WebApplicationContext createRootApplicationContext() {
		WebApplicationContext ctx = super.createRootApplicationContext();
		if (ctx != null && LOGGER.isDebugEnabled()) {
			LOGGER.debug("spring webapplicationcontext applicationName [" + ctx.getApplicationName() + "]");
		}
		BeanUtil.getInstance().setApplicationContext(ctx);
		return ctx;
	}

}
