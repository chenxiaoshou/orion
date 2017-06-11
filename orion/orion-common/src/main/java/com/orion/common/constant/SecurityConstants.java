package com.orion.common.constant;

public final class SecurityConstants {

	private SecurityConstants() {
	}

	/**
	 * CROS跨域请求
	 */
	public static final String VALUE_ACCESS_CONTROL_ALLOW_CREDENTIALS = "true";

	public static final String VALUE_ACCESS_CONTROL_ALLOW_HEADERS = "Origin,X-Requested-With,Content-Type,Accept,Cache-Control,X-Auth-Token";

	public static final String VALUE_ACCESS_CONTROL_ALLOW_METHODS = "GET,PUT,POST,DELETE,OPTIONS,HEAD";

	public static final String VALUE_ACCESS_CONTROL_ALLOW_ORIGIN = ".orion.com";

	public static final String VALUE_ACCESS_CONTROL_EXPOSE_HEADERS = "Origin,X-Requested-With,Content-Type,Accept,Cache-Control,X-Auth-Token";

	public static final String VALUE_ACCESS_CONTROL_MAX_AGE = String.valueOf(60 * 60 * 24); // 预请求有效期保留一天

	/**
	 * 认证授权使用的数据库schema
	 */
	public static final String AUTH_DB = "orion_auth";

	/**
	 * Spring Security
	 */
	public static final String HEADER_AUTH_TOKEN = "X-Auth-Token"; // header中保存的JWT的key

	public static final String HEADER_NEW_AUTH_TOKEN = "X-New-Auth-Token"; // reponse的header中保存自动续期之后新生成的Token头信息

	public static final long JWT_EXPIRATION = 4 * 60 * 1000L; // JWT过期时间：10分钟(表示到毫秒)

	public static final long LEFT_TIME_FOR_TOKEN_EXPIRATION = 2 * 60 * 1000L; // 距离过期还剩的时间毫秒数，用来确定自动刷新token的时间点

	public static final String PUBLIC_KEY_FILE_PATH = "keystore/publicKey.keystore";

	public static final String PRIVATE_KEY_FILE_PATH = "keystore/privateKey.keystore";

}
