package com.orion;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * JUnit4测试基类
 * @author John
 *
 */
public abstract class NormalBaseTest extends BaseTest {

	@Override
	public void setUp() {
		super.setUp();
		// 不使用mock模拟注入具体某个controller的情况下，可以使用全局设置
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

}
