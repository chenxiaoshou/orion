package com.polaris.config.web;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.web.Log4jServletFilter;
import org.springframework.core.annotation.Order;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.util.IntrospectorCleanupListener;

import com.polaris.common.constant.PolarisConstants;

@Order(0) // 指定配置文件的启动顺序
public class WebConfig implements WebApplicationInitializer {
	
	private static final Logger LOGGER = LogManager.getLogger(WebConfig.class);

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		
		LOGGER.warn("init...");
		
		// log4j2初始化参数，关闭自动初始化，这样可以在下面自动配置
//		servletContext.setInitParameter(Log4jWebSupport.IS_LOG4J_AUTO_INITIALIZATION_DISABLED, "true");
//		servletContext.setInitParameter(Log4jWebSupport.LOG4J_CONFIG_LOCATION, "classpath:log4j2.xml");

		// log4j2 监听器
//		servletContext.addListener(Log4jServletContextListener.class);

		// Spring资源清理监听器
		servletContext.addListener(IntrospectorCleanupListener.class);

		/*
		 * log4j2 过滤器 log4j2默认会在容器启动的时候启动一个默认的log4jServletFilter，
		 * 并且名字就叫做‘log4jServletFilter’，这里如果再重新申明一个的话，会导致返回一个null，出现空指针问题
		 */
		Log4jServletFilter log4jServletFilter = new Log4jServletFilter();
		FilterRegistration.Dynamic log4jConfig = servletContext.addFilter("log4jServletFilter", log4jServletFilter);
		log4jConfig.addMappingForUrlPatterns(
				EnumSet.of(DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.REQUEST, DispatcherType.ASYNC,
						DispatcherType.ERROR),
				true, // 在所有当前已经被声明的 Filter的前面先匹配 URL
				"/*");

		// CharacterEncodingFilter 过滤器
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter(PolarisConstants.CHAESET_UTF_8,
				true);
		FilterRegistration.Dynamic characterEncodingConfig = servletContext.addFilter("characterEncodingFilter",
				characterEncodingFilter);
		// 这里限定所有客户端请求、服务器forward、服务器include, 服务器ASYNC的请求全都需要经过filter处理
		characterEncodingConfig.addMappingForUrlPatterns(
				EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ASYNC,
						DispatcherType.ERROR),
				false, // 在所有当前已经被声明的 Filter 的前面先匹配 URL
				"/*");

		// OpenEntityManagerInViewFilter
		// 过滤器，避免在页面上出现懒加载异常，如果我们全部使用json作为传输的话则可以不配置
		// 这里如果使用的是hibernate的话， 需要配置的是openSessionInViewFilter
		OpenEntityManagerInViewFilter openEntityManagerInViewFilter = new OpenEntityManagerInViewFilter();
		FilterRegistration.Dynamic openEntityManagerInViewConfig = servletContext
				.addFilter("openEntityManagerInViewFilter", openEntityManagerInViewFilter);
		openEntityManagerInViewConfig.addMappingForUrlPatterns(
				EnumSet.of(DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.REQUEST,
						DispatcherType.ASYNC),
				true, // 在所有当前已经被声明的 Filter的前面先匹配 URL
				"/*");

		// HiddenHttpMethodFilter过滤器，使java支持restful风格中http的put和delete方法
		HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
		FilterRegistration.Dynamic hiddenHttpMethodConfig = servletContext.addFilter("hiddenHttpMethodFilter",
				hiddenHttpMethodFilter);
		hiddenHttpMethodConfig.addMappingForUrlPatterns(
				EnumSet.of(DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.REQUEST,
						DispatcherType.ASYNC),
				true, // 在所有当前已经被声明的 Filter的前面先匹配 URL
				"/*");

	}

}
