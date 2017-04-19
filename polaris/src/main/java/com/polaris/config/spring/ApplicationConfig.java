package com.polaris.config.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.polaris.config.database.AppLogDBConfig;
import com.polaris.config.database.DBConfig;

@Configuration
@Import(value = { DBConfig.class, AppLogDBConfig.class })
@ComponentScan(basePackages = { "com.polaris.*" }, excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = { Controller.class, ControllerAdvice.class }) })
@EnableAsync // 启用异步支持，可以使用@Async注解配置一个异步方法
@PropertySource("classpath:config.properties") // 注入配置文件
@EnableAspectJAutoProxy(proxyTargetClass = true) // 启用Spring切面自动代理，用于AOP, 并指定使用CGLIB基于类的代理
public class ApplicationConfig {

	// TODO 其他后续可能会使用到的配置信息

}
