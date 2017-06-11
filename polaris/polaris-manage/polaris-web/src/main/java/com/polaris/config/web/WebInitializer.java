package com.polaris.config.web;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.core.annotation.Order;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.util.IntrospectorCleanupListener;

import com.polaris.common.constant.PolarisConstants;
import com.polaris.common.constant.SecurityConstants;
import com.polaris.common.filter.AccessControlFilter;
import com.polaris.common.listener.ContextDestroyListener;

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
		 * 自定义监听器，用于应用关闭时的资源清理工作
		 */
		servletContext.addListener(ContextDestroyListener.class);
		
		/**
		 * Spring提供的监听器，用于关闭时清理工作
		 */
		servletContext.addListener(IntrospectorCleanupListener.class);
		
		/**
		 * CharacterEncodingFilter 过滤器
		 */
		servletContext
			.addFilter("characterEncodingFilter",new CharacterEncodingFilter(PolarisConstants.CHAESET_UTF_8, true))
				.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, 
						DispatcherType.INCLUDE,DispatcherType.ASYNC, DispatcherType.ERROR), false, PolarisConstants.POLARIS_API_MAPPING_URL_PATTERN);

		/**
		 * OpenEntityManagerInViewFilter
		 * 过滤器，避免在页面上出现懒加载异常，如果我们全部使用json作为传输的话则可以不配置, 这里如果使用的是hibernate的话，
		 * 需要配置的是openSessionInViewFilter
		 */
		servletContext.addFilter("openEntityManagerInViewFilter", new OpenEntityManagerInViewFilter())
				.addMappingForUrlPatterns(EnumSet.of(DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.REQUEST,
						DispatcherType.ASYNC, DispatcherType.ERROR), true, PolarisConstants.POLARIS_API_MAPPING_URL_PATTERN);

		/**
		 * HiddenHttpMethodFilter过滤器，使java支持restful风格中http的put和delete方法
		 */
		servletContext.addFilter("hiddenHttpMethodFilter", new HiddenHttpMethodFilter()).addMappingForUrlPatterns(
				EnumSet.of(DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.REQUEST, DispatcherType.ASYNC,DispatcherType.ERROR),
						true, PolarisConstants.POLARIS_API_MAPPING_URL_PATTERN);

		/**
		 * AccessControlFilter,自定义的过滤器，用来处理Ajax跨域请求的问题
		 */
		Map<String, String> initParameters = new HashMap<>();
		initParameters.put(AccessControlFilter.ACCESS_CONTROL_ALLOW_CREDENTIALS, SecurityConstants.VALUE_ACCESS_CONTROL_ALLOW_CREDENTIALS);
		initParameters.put(AccessControlFilter.ACCESS_CONTROL_ALLOW_HEADERS, SecurityConstants.VALUE_ACCESS_CONTROL_ALLOW_HEADERS);
		initParameters.put(AccessControlFilter.ACCESS_CONTROL_ALLOW_METHODS, SecurityConstants.VALUE_ACCESS_CONTROL_ALLOW_METHODS);
		initParameters.put(AccessControlFilter.ACCESS_CONTROL_ALLOW_ORIGIN, SecurityConstants.VALUE_ACCESS_CONTROL_ALLOW_ORIGIN);
		initParameters.put(AccessControlFilter.ACCESS_CONTROL_EXPOSE_HEADERS, SecurityConstants.VALUE_ACCESS_CONTROL_EXPOSE_HEADERS);
		initParameters.put(AccessControlFilter.ACCESS_CONTROL_MAX_AGE, SecurityConstants.VALUE_ACCESS_CONTROL_MAX_AGE);
		FilterRegistration.Dynamic accessControlFilter = servletContext.addFilter("accessControlFilter", new AccessControlFilter());
		accessControlFilter.setInitParameters(initParameters);
		accessControlFilter.addMappingForUrlPatterns(
				EnumSet.of(DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.REQUEST, DispatcherType.ASYNC,DispatcherType.ERROR),
						true, PolarisConstants.POLARIS_API_MAPPING_URL_PATTERN);
	}

}
