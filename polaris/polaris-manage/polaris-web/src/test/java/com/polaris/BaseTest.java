package com.polaris;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.polaris.common.utils.SpringUtil;
import com.polaris.config.spring.ApplicationConfig;
import com.polaris.config.springmvc.PolarisMvcConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({ @ContextConfiguration(name = "parent", classes = ApplicationConfig.class),
		@ContextConfiguration(name = "child", classes = PolarisMvcConfig.class) })
@WebAppConfiguration(value = "src/main/webapp")
@Transactional(transactionManager = "transactionManager")
@Rollback(true)
public abstract class BaseTest {

	@Autowired
	protected WebApplicationContext context;

	protected MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build(); // 不使用mock模拟注入具体某个controller的情况下，可以使用全局设置
		SpringUtil.getInstance().setApplicationContext(context);
	}

}
