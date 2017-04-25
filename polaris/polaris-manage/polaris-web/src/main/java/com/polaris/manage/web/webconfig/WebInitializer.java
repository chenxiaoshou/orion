package com.polaris.manage.web.webconfig;

import javax.servlet.ServletRegistration;

import org.springframework.core.annotation.Order;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.polaris.common.utils.BeanUtil;
import com.polaris.manage.web.appconfig.ApplicationConfig;

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
	 * 配置应用的上下文，即所有不包括 SpringMVC 等 Web 配置之外的其他Spring上下文配置， 比如：Spring、Hibernate、AOP 等的配置类
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

	/**
	 * 配置请求路径和DispatcherServlet一致的过滤器, 不一致的可以在WebConfig中配置
	 */
	/*@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding(PolarisConstants.CHAESET_UTF_8);
		characterEncodingFilter.setForceEncoding(true);
		return new Filter[] { characterEncodingFilter };
	}*/
	
	/**
     * 可以注册DispatcherServlet的初始化参数，等等
     */
   @Override
   protected void customizeRegistration(ServletRegistration.Dynamic registration) {        
       //registration.setInitParameter("spring.profiles.active", "default");
   	
   }

   /*
    * 创建一个可以在非spring管理bean中获得spring管理的相关bean的对象: BeanUtil.getBean(beanName);
    */
	@Override
	protected WebApplicationContext createRootApplicationContext() {
		WebApplicationContext ctx = super.createRootApplicationContext();
		BeanUtil.getInstance().setApplicationContext(ctx);
		return ctx;
	}

}
