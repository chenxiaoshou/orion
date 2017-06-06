package com.polaris.config.web;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.polaris.common.constant.PolarisConstants;

/**
 * 配置Druid监控Servlet和Filter
 * 
 * @author dong
 *
 */
@Order(3)
public class DruidInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {

		// Filter
		FilterRegistration.Dynamic druidWebStatFilter = servletContext.addFilter("DruidWebStatFilter",
				new WebStatFilter());
		// 静态资源过滤
		druidWebStatFilter.setInitParameter("exclusions", "*.js, *.gif, *.jpg, *.png, *.css, *.ico, /druid/*");
		// 打印出当前sql的使用用户信息, 提供session中的name
		druidWebStatFilter.setInitParameter("principalSessionName", PolarisConstants.DRUID_STAT_PRINCIPAL_SESSION_NAME);
		// druid 0.2.7版本开始支持profile，配置profileEnable能够监控单个url调用的sql列表
		druidWebStatFilter.setInitParameter("druidWebStatFilter", "true");
		druidWebStatFilter.addMappingForUrlPatterns(
				EnumSet.of(DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.REQUEST,
						DispatcherType.ASYNC),
				true, // 在所有当前已经被声明的 Filter的前面先匹配 URL
				PolarisConstants.POLARIS_API_MAPPING_URL_PATTERN);

		// Servlet
		ServletRegistration.Dynamic druidStatView = servletContext.addServlet("DruidStatView", new StatViewServlet());
		druidStatView.setInitParameter(StatViewServlet.PARAM_NAME_USERNAME, PolarisConstants.DRUID_STATVIEW_USERNAME);
		druidStatView.setInitParameter(StatViewServlet.PARAM_NAME_PASSWORD, PolarisConstants.DRUID_STATVIEW_PASSWORD);
		druidStatView.setInitParameter("allow", PolarisConstants.DRUID_STAT_ALLOW_IP); // 指定可以访问统计页面的IP，提高安全性
		// 在StatViewSerlvet输出的html页面中，有一个功能是Reset
		// All，执行这个操作之后，会导致所有计数器清零，重新计数。你可以通过配置参数关闭它。
		druidStatView.setInitParameter("resetEnable", "false");
		druidStatView.addMapping(new String[] { PolarisConstants.DRUID_MAPPING_URL_PATTERN });
	}

}
