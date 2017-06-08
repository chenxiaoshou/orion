package com.polaris.manage.service.srv.component.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.polaris.common.constant.RedisConstants;
import com.polaris.common.utils.JsonUtil;
import com.polaris.manage.service.dto.component.UserInfoCache;
import com.polaris.manage.service.srv.component.RedisService;

@Service
public class RedisServiceImpl implements RedisService {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	public void storeUserInfo(String token, UserInfoCache userInfoCache) {
		this.stringRedisTemplate.opsForHash().put(RedisConstants.KEY_USER_INFO, token, JsonUtil.toJSON(userInfoCache));
	}

	@Override
	public void removeUserInfo(String token) {
		this.stringRedisTemplate.opsForHash().delete(RedisConstants.KEY_USER_INFO, token);
	}

	@Override
	public UserInfoCache getUserInfo(String token) {
		Object value = this.stringRedisTemplate.opsForHash().get(RedisConstants.KEY_USER_INFO, token);
		if (value != null) {
			return JsonUtil.fromJSON((String) value, UserInfoCache.class);
		}
		return null;
	}

}
