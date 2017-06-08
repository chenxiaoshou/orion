package com.polaris.manage.service.srv.component;

import com.polaris.manage.service.dto.component.UserInfoCache;

public interface RedisService {

	/**
	 * key保存的是用户的token，value保存的是UserInfoCache经过json序列化之后的字符串
	 * @param key
	 * @param value
	 */
	void storeUserInfo(String token, UserInfoCache userInfoCache);

	void removeUserInfo(String token);

}
