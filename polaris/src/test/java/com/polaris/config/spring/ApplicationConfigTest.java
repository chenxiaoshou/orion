package com.polaris.config.spring;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.polaris.config.TestConfig;
import com.polaris.config.springmvc.PolarisServletConfig;

public class ApplicationConfigTest extends TestConfig {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private ApplicationConfig config;

	@Autowired
	private PolarisServletConfig mvcConfig;

	@Test
	public void test() {
		System.out.println("dataSource >> " + dataSource);
		System.out.println("config >> " + config);
		System.out.println("mvcConfig >> " + mvcConfig);
	}

}
