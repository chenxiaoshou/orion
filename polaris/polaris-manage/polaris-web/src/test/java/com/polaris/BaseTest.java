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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.polaris.common.utils.SpringUtil;
import com.polaris.config.spring.ApplicationConfig;
import com.polaris.config.springmvc.SpringMvcConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({ @ContextConfiguration(name = "parent", classes = ApplicationConfig.class),
		@ContextConfiguration(name = "child", classes = SpringMvcConfig.class) })
@WebAppConfiguration(value = "src/main/webapp")
@Transactional(transactionManager = "transactionManager")
@Rollback(false)
public abstract class BaseTest {

	@Autowired
	protected WebApplicationContext context;

	protected MockMvc mockMvc;

	@Before
	public void setUp() {
		SpringUtil.getInstance().setApplicationContext(context);
	}

}
