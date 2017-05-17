package com.polaris;

/**
 * Mockito测试框架基类
 * @author John
 *
 */
public abstract class MockBaseTest extends BaseTest {

	@Override
	public void setUp() {
		super.setUp();
		mockMvc = null;
	}

}
