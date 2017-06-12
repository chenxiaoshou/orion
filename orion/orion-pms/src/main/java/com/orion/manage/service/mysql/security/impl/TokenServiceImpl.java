package com.orion.manage.service.mysql.security.impl;

import java.text.MessageFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.orion.common.constant.SecurityConstants;
import com.orion.common.dic.SourceTypeEnum;
import com.orion.common.exception.AppException;
import com.orion.common.utils.JwtUtil;
import com.orion.manage.model.mysql.auth.User;
import com.orion.manage.model.mysql.security.SecurityUser;
import com.orion.manage.service.mysql.component.RedisService;
import com.orion.manage.service.mysql.security.TokenService;

import net.sf.json.JSONObject;

@Service("tokenService")
public class TokenServiceImpl implements TokenService {

	private static final Logger LOGGER = LogManager.getLogger(TokenServiceImpl.class);

	private static String errMsg = "Parse {0} from JWT [{1}] failure!";

	@Autowired
	private RedisService redisService;

	public String generateToken(UserDetails userDetails, String tokenSigner, String remoteHost, SourceTypeEnum source) {
		Map<String, Object> payloads = JwtUtil.buildNormalJwtPayloads();
		SecurityUser securityUser = (SecurityUser) userDetails;
		payloads.put(JwtUtil.CLAIMS_ISS, tokenSigner);
		payloads.put(JwtUtil.CLAIMS_SUB, source.getCode());
		payloads.put(JwtUtil.CLAIMS_AUD, remoteHost);
		payloads.put(JwtUtil.CLAIMS_USERNAME, securityUser.getUser().getUsername());
		payloads.put(JwtUtil.CLAIMS_USERID, securityUser.getUser().getId());
		String roles = generateRoles(securityUser);
		payloads.put(JwtUtil.CLAIMS_ROLES, roles);
		String token;
		try {
			token = JwtUtil.encode(payloads);
		} catch (Exception e) {
			LOGGER.error("生成Token失败！", e);
			token = null;
		}
		return token;
	}

	public Boolean canTokenBeRefreshed(String token, LocalDateTime lastPasswordResetTime) {
		return isRefrehedTimeValid(token); // token还处在可刷新期内，就可以换取新token
	}

	public String refreshToken(String token) {
		String refreshedToken;
		try {
			final JSONObject payload = JwtUtil.getPayload(token);
			payload.put(JwtUtil.CLAIMS_IAT, generateCurrentDate());
			payload.put(JwtUtil.CLAIMS_EXP, generateExpirationDate());
			refreshedToken = JwtUtil.encode(payload.toString());
		} catch (Exception e) {
			throw new AppException("刷新Token失败", e);
		}
		return refreshedToken;
	}

	private LocalDateTime generateCurrentDate() {
		return LocalDateTime.now();
	}

	private LocalDateTime generateExpirationDate() {
		return LocalDateTime.ofInstant(
				Instant.ofEpochMilli(System.currentTimeMillis() + SecurityConstants.JWT_EXPIRATION),
				ZoneId.systemDefault());
	}

	public Boolean isTokenAvailable(SourceTypeEnum source, String token, UserDetails userDetails) {
		SecurityUser securityUser = (SecurityUser) userDetails;
		User user = securityUser.getUser();
		final String userId = getUserIdFromToken(token);
		final String username = getUsernameFromToken(token);
		final LocalDateTime createTime = getCreateTimeFromToken(token);
		final LocalDateTime expiration = getExpirationDateFromToken(token);
		boolean isTokenAvailable = true;
		boolean isNull = StringUtils.isBlank(userId) || StringUtils.isBlank(username) || createTime == null
				|| expiration == null;
		if (isNull || !username.equals(user.getUsername()) || !userId.equals(user.getId())) {
			isTokenAvailable = false;
		} else if (!JwtUtil.verifyJwtToken(token)) {
			isTokenAvailable = false;
			LOGGER.debug("username [" + username + "] token is illegal.");
		} else if (isTokenExpired(token)) {
			isTokenAvailable = false;
			LOGGER.debug("username [" + username + "] token is expired.");
		} else if (isCreateTimeBeforeLastPasswordResetTime(createTime, user.getLastPasswordResetTime())) {
			isTokenAvailable = false;
			LOGGER.debug("username [" + username + "] create time before last password reset time.");
		} else if (!existsInRedis(source, token)) {
			isTokenAvailable = false;
			LOGGER.debug("username [" + username + "] token not exists in redis!");
		}
		return isTokenAvailable;
	}

	private boolean existsInRedis(SourceTypeEnum source, String authToken) {
		String userId = this.getUserIdFromToken(authToken);
		String tokenInRedis = this.redisService.getUserIdToken(source, userId);
		return StringUtils.isNoneBlank(tokenInRedis) && tokenInRedis.equalsIgnoreCase(authToken)
				&& this.redisService.getTokenUserInfo(source, authToken) != null;
	}

	public String getUsernameFromToken(String token) {
		String username = null;
		try {
			if (StringUtils.isNotBlank(token)) {
				final JSONObject payload = JwtUtil.getPayload(token);
				username = payload.getString(JwtUtil.CLAIMS_USERNAME);
			}
		} catch (Exception e) {
			LOGGER.debug(MessageFormat.format(errMsg, "username", token), e);
			username = null;
		}
		return username;
	}

	public String getRolesFromToken(String token) {
		String roles = null;
		try {
			if (StringUtils.isNotBlank(token)) {
				final JSONObject payload = JwtUtil.getPayload(token);
				roles = payload.getString(JwtUtil.CLAIMS_ROLES);
			}
		} catch (Exception e) {
			LOGGER.debug(MessageFormat.format(errMsg, "roles", token), e);
			roles = null;
		}
		return roles;
	}

	public String getUserIdFromToken(String token) {
		String userId = null;
		try {
			if (StringUtils.isNotBlank(token)) {
				final JSONObject payload = JwtUtil.getPayload(token);
				userId = payload.getString(JwtUtil.CLAIMS_USERID);
			}
		} catch (Exception e) {
			LOGGER.debug(MessageFormat.format(errMsg, "userId", token), e);
			userId = null;
		}
		return userId;
	}

	public LocalDateTime getCreateTimeFromToken(String token) {
		LocalDateTime createTime = null;
		try {
			if (StringUtils.isNotBlank(token)) {
				final JSONObject payload = JwtUtil.getPayload(token);
				createTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(payload.getLong(JwtUtil.CLAIMS_IAT)),
						ZoneId.systemDefault());
			}
		} catch (Exception e) {
			LOGGER.debug(MessageFormat.format(errMsg, "createTime", token), e);
			createTime = null;
		}
		return createTime;
	}

	public LocalDateTime getRefreshTimeFromToken(String token) {
		LocalDateTime refreshTime = null;
		try {
			if (StringUtils.isNotBlank(token)) {
				final JSONObject payload = JwtUtil.getPayload(token);
				refreshTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(payload.getLong(JwtUtil.CLAIMS_REF)),
						ZoneId.systemDefault());
			}
		} catch (Exception e) {
			LOGGER.debug(MessageFormat.format(errMsg, "refreshTime", token), e);
			refreshTime = null;
		}
		return refreshTime;
	}

	public LocalDateTime getExpirationDateFromToken(String token) {
		LocalDateTime expiration = null;
		try {
			if (StringUtils.isNotBlank(token)) {
				final JSONObject payload = JwtUtil.getPayload(token);
				expiration = LocalDateTime.ofInstant(Instant.ofEpochMilli(payload.getLong(JwtUtil.CLAIMS_EXP)),
						ZoneId.systemDefault());
			}
		} catch (Exception e) {
			LOGGER.debug(MessageFormat.format(errMsg, "expiration", token), e);
			expiration = null;
		}
		return expiration;
	}

	public String getRemoteHostFromToken(String token) {
		String remoteHost = null;
		try {
			if (StringUtils.isNotBlank(token)) {
				final JSONObject payload = JwtUtil.getPayload(token);
				remoteHost = payload.getString(JwtUtil.CLAIMS_AUD);
			}
		} catch (Exception e) {
			LOGGER.debug(MessageFormat.format(errMsg, "remoteHost", token), e);
			remoteHost = null;
		}
		return remoteHost;
	}

	public SourceTypeEnum getSourceTypeFromToken(String token) {
		int code = 0;
		try {
			if (StringUtils.isNotBlank(token)) {
				final JSONObject payload = JwtUtil.getPayload(token);
				code = payload.getInt(JwtUtil.CLAIMS_SUB);
			}
		} catch (Exception e) {
			LOGGER.debug(MessageFormat.format(errMsg, "sourceType", token), e);
		}
		return SourceTypeEnum.getSourceTypeByCode(code);
	}

	// 生成用户角色字符串拼接
	private String generateRoles(SecurityUser securityUser) {
		Collection<? extends GrantedAuthority> authorities = securityUser.getAuthorities();
		List<String> roles = new ArrayList<>();
		for (GrantedAuthority authority : authorities) {
			if (!roles.contains(authority.getAuthority())) {
				roles.add(authority.getAuthority());
			}
		}
		return StringUtils.join(roles, ",");
	}

	// 判断token是否快要过期（距离过期时间半小时以内，即可判定快要过期）
	public Boolean isTokenWillExpire(String token) {
		LocalDateTime expiration = getExpirationDateFromToken(token);
		if (expiration == null) {
			return true;
		}
		LocalDateTime beforeHalfHours = expiration.minusSeconds(SecurityConstants.LEFT_TIME_FOR_TOKEN_EXPIRATION);
		return !beforeHalfHours.isAfter(generateCurrentDate());
	}

	// 判断token是不是还处在可刷新期内
	public boolean isRefrehedTimeValid(String token) {
		LocalDateTime refreshTime = getRefreshTimeFromToken(token);
		if (refreshTime == null) {
			return false;
		}
		return generateCurrentDate().isBefore(refreshTime);
	}

	// 判断token是否已经过期
	public Boolean isTokenExpired(String token) {
		final LocalDateTime expiration = getExpirationDateFromToken(token);
		if (expiration == null) {
			return true;
		}
		return !expiration.isAfter(generateCurrentDate());
	}

	// 判断是否是在密码重置之前创建的，如果是的话，需要判定为失效token，用户需要重新登录
	public Boolean isCreateTimeBeforeLastPasswordResetTime(LocalDateTime createTime, LocalDateTime lastPasswordReset) {
		return (lastPasswordReset != null && !createTime.isAfter(lastPasswordReset));
	}

}
