package com.polaris.common.constant;

/**
 * Redis相关常量
 * <p>
 * 命名规范，字段名大写，单词之间采用下划线分割; 字段值按照类名书写方式，首字母大写驼峰写法；
 * 必须写明每个常量的业务意义。
 * </p>
 * 
 * @author John
 *
 */
public final class RedisConstants {

	private RedisConstants() {

	}

	/**
	 * 测试Redis服务器连接情况时使用的key
	 */
	public static final String PING = "Ping";

	/**
	 * 用户登录后，保存其相关token和身份认证信息的key
	 */
	public static final String KEY_USER_INFO = "UserInfo";

}
