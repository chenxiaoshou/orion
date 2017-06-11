package com.orion.manage.service.mysql.component;

import java.time.LocalDateTime;

import com.orion.manage.service.dto.component.UserInfoCache;

public interface RedisService {

	/**
	 * 保存token：userinfo键值对信息
	 * 顶级key-value存储结构，可配置数据的到期日期
	 * @param token
	 * @param userInfoCache
	 */
	void storeTokenUserInfo(String token, UserInfoCache userInfoCache, LocalDateTime expiration);

	/**
	 * 从Redis服务器删除指定Token的用户信息缓存
	 * 
	 * @param token
	 */
	void removeTokenUserInfo(String token);

	/**
	 * 从Redis服务器查找指定Token的用户信息缓存
	 * 
	 * @param token
	 * @return
	 */
	UserInfoCache getTokenUserInfo(String token);

	/**
	 * HASH结构，key=userid:token, hashKey=userId, value=token
	 * 每次用户登录都会存储一条以useid为hashKey的数据，覆盖掉旧的数据并把这条旧Token数据返回。
	 * 使用该旧token再去删除保存在token:userinfo结构redis表里的用户信息数据。
	 * 
	 * @param userId
	 * @param token
	 */
	String storeUserIdToken(String userId, String token);
	
	String getUserIdToken(String userId);
	
	void removeUserIdToken(String userId);

	void storeUserIdTokenAndClearOldTokenUserInfo(String userId, String token);

	/**
	 * 将要过期的token，在filter中会被刷新换成新的token(放置在header中返回到客户端)，旧的token就会在这里存入，标记为过期
	 * 这样下次filter不会再次重复刷新该旧token
	 * @param token
	 */
	public void storeToBeExpiredToken(String token);
	
	String getToBeExpiredToken(String token);

}
