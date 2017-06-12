package com.orion.manage.service.mysql.component.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.orion.common.constant.RedisConstants;
import com.orion.common.dic.SourceTypeEnum;
import com.orion.common.utils.DateUtil;
import com.orion.common.utils.JsonUtil;
import com.orion.manage.service.dto.component.UserInfoCache;
import com.orion.manage.service.mysql.component.RedisService;

@Service("redisService")
public class RedisServiceImpl implements RedisService {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public void storeTokenUserInfo(SourceTypeEnum source, String token, UserInfoCache userInfoCache,
			LocalDateTime expiration) {
		long timeout = 0;
		String key = buildTokenUserInfoKey(source, token);
		if (expiration != null
				&& (timeout = DateUtil.getIntervalSeconds(LocalDateTime.now(ZoneId.systemDefault()), expiration)) > 0) {
			this.stringRedisTemplate.boundValueOps(key).set(userInfoCache.toJson(), timeout, TimeUnit.SECONDS);
		} else {
			this.stringRedisTemplate.boundValueOps(key).set(userInfoCache.toJson());
		}
	}

	private String buildTokenUserInfoKey(SourceTypeEnum source, String token) {
		return RedisConstants.KEY_TOKEN_USERINFO + source + "||" + token;
	}

	@Override
	public UserInfoCache removeTokenUserInfo(SourceTypeEnum source, String token) {
		UserInfoCache userInfoCache = this.getTokenUserInfo(source, token);
		this.stringRedisTemplate.delete(buildTokenUserInfoKey(source, token));
		return userInfoCache;
	}

	@Override
	public UserInfoCache getTokenUserInfo(SourceTypeEnum source, String token) {
		String value = this.stringRedisTemplate.boundValueOps(buildTokenUserInfoKey(source, token)).get();
		if (value != null) {
			return JsonUtil.fromJSON((String) value, UserInfoCache.class);
		}
		return null;
	}

	@Override
	public String storeUserIdToken(SourceTypeEnum source, String userId, String token, LocalDateTime expiration) {
		Object oldToken = this.getUserIdToken(source, userId);
		long timeout = 0;
		String key = builderUserIdTokenKey(source, userId);
		if (expiration != null
				&& (timeout = DateUtil.getIntervalSeconds(LocalDateTime.now(ZoneId.systemDefault()), expiration)) > 0) {
			this.stringRedisTemplate.opsForValue().set(key, token, timeout, TimeUnit.SECONDS);
		} else {
			this.stringRedisTemplate.opsForValue().set(key, token);
		}
		return oldToken == null ? null : (String) oldToken;
	}

	private String builderUserIdTokenKey(SourceTypeEnum source, String userId) {
		return RedisConstants.KEY_USERID_TOKEN + source + "||" + userId;
	}

	@Override
	public String removeUserIdToken(SourceTypeEnum source, String userId) {
		String token = this.getUserIdToken(source, userId);
		this.stringRedisTemplate.delete(builderUserIdTokenKey(source, userId));
		return token;
	}

	@Override
	public String getUserIdToken(SourceTypeEnum source, String userId) {
		Object token = this.stringRedisTemplate.opsForValue().get(builderUserIdTokenKey(source, userId));
		return token == null ? null : (String) token;
	}

	@Override
	public void storeUserIdTokenAndClearOldTokenUserInfo(SourceTypeEnum source, String userId, String token,
			LocalDateTime expiration) {
		String oldToken = storeUserIdToken(source, userId, token, expiration);
		if (StringUtils.isNotBlank(oldToken)) {
			removeTokenUserInfo(source, oldToken);
		}
	}

}
