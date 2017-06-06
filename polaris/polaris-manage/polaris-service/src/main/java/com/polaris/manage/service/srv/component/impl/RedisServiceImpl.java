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
		stringRedisTemplate.opsForHash().put(RedisConstants.KEY_USER_INFO, token, JsonUtil.toJSON(userInfoCache));
	}

}
