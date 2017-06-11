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
import com.orion.manage.service.mysql.security.TokenService;

@Service("redisService")
public class RedisServiceImpl implements RedisService {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private TokenService tokenService;

	@Override
	public void storeTokenUserInfo(String token, UserInfoCache userInfoCache, LocalDateTime expiration) {
		this.stringRedisTemplate.boundValueOps(RedisConstants.KEY_TOKEN_USERINFO + token).set(userInfoCache.toJson());
		long timeout = DateUtil.getIntervalSeconds(LocalDateTime.now(ZoneId.systemDefault()), expiration);
		this.stringRedisTemplate.expire(RedisConstants.KEY_TOKEN_USERINFO + token, timeout, TimeUnit.SECONDS);
	}

	@Override
	public void removeTokenUserInfo(String token) {
		this.stringRedisTemplate.delete(RedisConstants.KEY_TOKEN_USERINFO + token);
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
	public String storeUserIdToken(String userId, String token) {
		// 取出旧数据，用户第一次登录时，此处可能为null
		Object data = this.stringRedisTemplate.opsForHash().get(RedisConstants.KEY_USERID_TOKEN, userId);
		// 存入新数据，覆盖旧数据
		this.stringRedisTemplate.opsForHash().put(RedisConstants.KEY_USERID_TOKEN, userId, token);
		return data == null ? null : (String) data;
	}

	@Override
	public void removeUserIdToken(String userId) {
		this.stringRedisTemplate.opsForHash().delete(RedisConstants.KEY_USERID_TOKEN, userId);
	}

	@Override
	public String getUserIdToken(String userId) {
		Object value = this.stringRedisTemplate.opsForHash().get(RedisConstants.KEY_USERID_TOKEN, userId);
		return value == null ? null : (String) value;
	}

	@Override
	public void storeUserIdTokenAndClearOldTokenUserInfo(String userId, String token) {
		String oldToken = storeUserIdToken(userId, token);
		if (StringUtils.isNotBlank(oldToken)) {
			removeTokenUserInfo(oldToken);
		}
	}

	@Override
	public void storeToBeExpiredToken(String token) {
		this.stringRedisTemplate.boundValueOps(RedisConstants.KEY_TO_BE_EXPIRED_TOKEN + token).set("ToBeExpired");
		LocalDateTime expiration = this.tokenService.getExpirationDateFromToken(token);
		long timeout = DateUtil.getIntervalSeconds(LocalDateTime.now(ZoneId.systemDefault()), expiration);
		this.stringRedisTemplate.expire(RedisConstants.KEY_TO_BE_EXPIRED_TOKEN + token, timeout, TimeUnit.SECONDS);
	}

	@Override
	public String getToBeExpiredToken(String token) {
		Object value = this.stringRedisTemplate.boundValueOps(RedisConstants.KEY_TO_BE_EXPIRED_TOKEN + token).get();
		return value == null ? null : (String) value;
	}

}
