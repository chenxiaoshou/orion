package com.orion.manage.service.mysql.component.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.orion.common.constant.RedisConstants;
import com.orion.common.utils.DateUtil;
import com.orion.common.utils.JsonUtil;
import com.orion.manage.service.dto.component.UserInfoCache;
import com.orion.manage.service.mysql.component.RedisService;

@Service("redisService")
public class RedisServiceImpl implements RedisService {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public void storeTokenUserInfo(String token, UserInfoCache userInfoCache, LocalDateTime expiration) {
		long timeout = 0;
		if (expiration != null
				&& (timeout = DateUtil.getIntervalSeconds(LocalDateTime.now(ZoneId.systemDefault()), expiration)) > 0) {
			this.stringRedisTemplate.boundValueOps(RedisConstants.KEY_TOKEN_USERINFO + token)
					.set(userInfoCache.toJson(), timeout, TimeUnit.SECONDS);
		} else {
			this.stringRedisTemplate.boundValueOps(RedisConstants.KEY_TOKEN_USERINFO + token)
					.set(userInfoCache.toJson());
		}
	}

	@Override
	public UserInfoCache removeTokenUserInfo(String token) {
		UserInfoCache userInfoCache = this.getTokenUserInfo(RedisConstants.KEY_TOKEN_USERINFO + token);
		this.stringRedisTemplate.delete(RedisConstants.KEY_TOKEN_USERINFO + token);
		return userInfoCache;
	}

	@Override
	public UserInfoCache getTokenUserInfo(String token) {
		String value = this.stringRedisTemplate.boundValueOps(RedisConstants.KEY_TOKEN_USERINFO + token).get();
		if (value != null) {
			return JsonUtil.fromJSON((String) value, UserInfoCache.class);
		}
		return null;
	}

	@Override
	public String storeUserIdToken(String userId, String token, LocalDateTime expiration) {
		Object oldToken = this.stringRedisTemplate.opsForValue().get(RedisConstants.KEY_USERID_TOKEN + userId);
		long timeout = 0;
		if (expiration != null
				&& (timeout = DateUtil.getIntervalSeconds(LocalDateTime.now(ZoneId.systemDefault()), expiration)) > 0) {
			this.stringRedisTemplate.opsForValue().set(RedisConstants.KEY_USERID_TOKEN + userId, token, timeout,
					TimeUnit.SECONDS);
		} else {
			this.stringRedisTemplate.opsForValue().set(RedisConstants.KEY_USERID_TOKEN + userId, token);
		}
		return oldToken == null ? null : (String) oldToken;
	}

	@Override
	public String removeUserIdToken(String userId) {
		String token = this.getUserIdToken(RedisConstants.KEY_USERID_TOKEN + userId);
		this.stringRedisTemplate.delete(RedisConstants.KEY_USERID_TOKEN + userId);
		return token;
	}

	@Override
	public String getUserIdToken(String userId) {
		Object token = this.stringRedisTemplate.opsForValue().get(RedisConstants.KEY_USERID_TOKEN + userId);
		return token == null ? null : (String) token;
	}

	@Override
	public void storeUserIdTokenAndClearOldTokenUserInfo(String userId, String token, LocalDateTime expiration) {
		String oldToken = storeUserIdToken(userId, token, expiration);
		if (StringUtils.isNotBlank(oldToken)) {
			removeTokenUserInfo(oldToken);
		}
	}

}
