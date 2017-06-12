package com.orion.manage.service.mysql.component;

import java.time.LocalDateTime;

import com.orion.manage.service.dto.component.UserInfoCache;

public interface RedisService {

	/**
	 * 保存token：userinfo键值对信息 顶级key-value存储结构，可配置数据的到期日期
	 * 
	 * @param token
	 * @param userInfoCache
	 */
	void storeTokenUserInfo(String token, UserInfoCache userInfoCache, LocalDateTime expiration);

	/**
	 * 从Redis服务器删除指定Token的用户信息缓存,并返回删除的数据
	 * 
	 * @param token
	 */
	UserInfoCache removeTokenUserInfo(String token);

	/**
	 * 从Redis服务器查找指定Token的用户信息缓存
	 * 
	 * @param token
	 * @return
	 */
	UserInfoCache getTokenUserInfo(String token);

	/**
	 * key=userid:token, value=token
	 * 每次用户登录生成新token时，都会调用这里存储一条userid:token数据，覆盖掉旧的数据并把这条旧Token数据返回。
	 * 使用该旧token再去删除保存在token:userinfo表里的用户信息
	 * 
	 * @param userId
	 * @param token
	 */
	String storeUserIdToken(String userId, String token, LocalDateTime expiration);

	String getUserIdToken(String userId);

	/**
	 * 删除UserIdToken,并返回删除的数据
	 * @param userId
	 * @return
	 */
	String removeUserIdToken(String userId);

	/**
	 * 
	 * @param userId
	 * @param token
	 * @param expiration
	 */
	void storeUserIdTokenAndClearOldTokenUserInfo(String userId, String token, LocalDateTime expiration);

}
