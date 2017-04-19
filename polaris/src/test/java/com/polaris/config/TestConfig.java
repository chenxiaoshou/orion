package com.polaris.config;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.polaris.config.spring.ApplicationConfig;

//指定测试类的运行者  
@RunWith(SpringJUnit4ClassRunner.class)
//指定spring配置类 
@ContextConfiguration(classes = {ApplicationConfig.class})
//@WebAppConfiguration is a class-level annotation that is used to declare  
//that the ApplicationContext loaded for an integration test should be a WebApplicationContext.  
@WebAppConfiguration  
@Transactional
public class TestConfig {

}
