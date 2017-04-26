package com.polaris.config.web;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.core.annotation.Order;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.util.IntrospectorCleanupListener;

import com.polaris.common.constant.PolarisConstants;

/**
 * 原web.xml中的配置信息(不包括web Servlet的配置)
 * 
 * @author John
 *
 */
@Order(0) // 指定配置文件的启动顺序
public class WebInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		/**
		 * Spring资源清理监听器
		 */
		servletContext.addListener(IntrospectorCleanupListener.class);

		/**
		 * CharacterEncodingFilter 过滤器
		 */
		servletContext
			.addFilter("characterEncodingFilter",new CharacterEncodingFilter(PolarisConstants.CHAESET_UTF_8, true))
				.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, 
						DispatcherType.INCLUDE,DispatcherType.ASYNC, DispatcherType.ERROR),false,PolarisConstants.POLARIS_MAPPING_URL_PATTERN);

		/**
		 * OpenEntityManagerInViewFilter
		 * 过滤器，避免在页面上出现懒加载异常，如果我们全部使用json作为传输的话则可以不配置, 这里如果使用的是hibernate的话，
		 * 需要配置的是openSessionInViewFilter
		 */
		servletContext.addFilter("openEntityManagerInViewFilter", new OpenEntityManagerInViewFilter())
				.addMappingForUrlPatterns(EnumSet.of(DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.REQUEST,
						DispatcherType.ASYNC, DispatcherType.ERROR),true,PolarisConstants.POLARIS_MAPPING_URL_PATTERN);

		/**
		 * HiddenHttpMethodFilter过滤器，使java支持restful风格中http的put和delete方法
		 */
		servletContext.addFilter("hiddenHttpMethodFilter", new HiddenHttpMethodFilter()).addMappingForUrlPatterns(
				EnumSet.of(DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.REQUEST, DispatcherType.ASYNC,DispatcherType.ERROR),
						true,PolarisConstants.POLARIS_MAPPING_URL_PATTERN);

	}

}
