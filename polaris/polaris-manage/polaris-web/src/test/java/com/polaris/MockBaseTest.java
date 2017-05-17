package com.polaris;

/**
 * Mockito测试框架基类
 * 由于JUnit4设置的rollback=TRUE，所以没有办法真正向数据库添加数据，导致测试查找方法时，没有数据可用。
 * 所以涉及查找相关方法的测试使用mockit模拟框架来测试，其他使用普通JUnit4进行测试
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
