package com.polaris.security.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.polaris.common.constant.PolarisConstants;
import com.polaris.common.utils.DateUtil;
import com.polaris.common.utils.JwtUtil;
import com.polaris.manage.web.vo.auth.AuthInfo;
import com.polaris.security.model.SecurityUser;

import net.sf.json.JSONObject;

public class TokenUtil {

	private static final Logger LOGGER = LogManager.getLogger(TokenUtil.class);

	private static String errMsg = "从jwt [{0}]中获取{1}失败！";

	private TokenUtil() {
	}

	/**
	 * 生成jwt
	 * 
	 * @param userDetails
	 * @param device
	 * @return
	 */
	public static String generateToken(UserDetails userDetails, String tokenSign, Device device) {
		Map<String, Object> payloads = JwtUtil.buildNormalJwtPayloads();
		SecurityUser securityUser = (SecurityUser) userDetails;
		payloads.put(JwtUtil.CLAIMS_ISS, tokenSign);
		payloads.put(JwtUtil.CLAIMS_SUB, securityUser.getUser().getId());
		payloads.put(JwtUtil.CLAIMS_USERNAME, securityUser.getUser().getUsername());
		payloads.put(JwtUtil.CLAIMS_AUD, generateAudience(device));
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

	/**
	 * 生成token并组装一个AuthInfo返回
	 * 
	 * @param userDetails
	 * @param device
	 * @param tokenSign token签发者
	 * @return
	 */
	public static AuthInfo generateTokenAndBuildAuthInfo(UserDetails userDetails, String tokenSign, Device device) {
		Map<String, Object> payloads = JwtUtil.buildNormalJwtPayloads();
		SecurityUser securityUser = (SecurityUser) userDetails;
		payloads.put(JwtUtil.CLAIMS_ISS, tokenSign);
		payloads.put(JwtUtil.CLAIMS_SUB, securityUser.getUser().getId());
		payloads.put(JwtUtil.CLAIMS_USERNAME, securityUser.getUser().getUsername());
		payloads.put(JwtUtil.CLAIMS_AUD, generateAudience(device));
		String roles = generateRoles(securityUser);
		payloads.put(JwtUtil.CLAIMS_ROLES, roles);
		String token;
		try {
			token = JwtUtil.encode(payloads);
		} catch (Exception e) {
			LOGGER.error("生成Token失败！", e);
			token = null;
		}
		AuthInfo authInfo = new AuthInfo();
		authInfo.setCreateTime((Long) payloads.get(JwtUtil.CLAIMS_IAT));
		authInfo.setDevice(String.valueOf(payloads.get(JwtUtil.CLAIMS_AUD)));
		authInfo.setExpiration((Long) payloads.get(JwtUtil.CLAIMS_EXP));
		authInfo.setPublicKey(String.valueOf(payloads.get(JwtUtil.CLAIMS_PUBLICKEY)));
		authInfo.setToken(token);
		authInfo.setUserId(String.valueOf(payloads.get(JwtUtil.CLAIMS_SUB)));
		authInfo.setUsername(String.valueOf(payloads.get(JwtUtil.CLAIMS_USERNAME)));
		authInfo.setRoles(roles);
		return authInfo;
	}

	/**
	 * 判断当前用户是否能够刷新token
	 * 
	 * @param token
	 * @param lastPasswordResetTime
	 * @return
	 */
	public static Boolean canTokenBeRefreshed(String token, Date lastPasswordResetTime) {
		final Date createTime = getCreateTimeFromToken(token);
		return (!isCreateTimeBeforeLastPasswordResetTime(createTime, lastPasswordResetTime)
				&& (!isTokenExpired(token) || ignoreTokenExpiration(token)));
	}

	/**
	 * 刷新token并组装成AuthIfo返回
	 * 
	 * @param token
	 * @return
	 */
	public static AuthInfo refreshTokenAndBuildAuthInfo(String token) {
		String refreshedToken;
		JSONObject payload = null;
		try {
			payload = JwtUtil.getPayload(token);
			payload.put(JwtUtil.CLAIMS_IAT, generateCurrentDate());
			payload.put(JwtUtil.CLAIMS_EXP, generateExpirationDate());
			refreshedToken = JwtUtil.encode(payload.toString());
		} catch (Exception e) {
			LOGGER.error("刷新Token失败！", e);
			refreshedToken = null;
		}
		AuthInfo authInfo = new AuthInfo();
		if (payload != null) {
			authInfo.setCreateTime(payload.getLong(JwtUtil.CLAIMS_IAT));
			authInfo.setDevice(payload.getString(JwtUtil.CLAIMS_AUD));
			authInfo.setExpiration(payload.getLong(JwtUtil.CLAIMS_EXP));
			authInfo.setPublicKey(payload.getString(JwtUtil.CLAIMS_PUBLICKEY));
			authInfo.setToken(refreshedToken);
			authInfo.setUserId(payload.getString(JwtUtil.CLAIMS_SUB));
			authInfo.setUsername(payload.getString(JwtUtil.CLAIMS_USERNAME));
			authInfo.setRoles(payload.getString(JwtUtil.CLAIMS_ROLES));
		}
		return authInfo;
	}

	/**
	 * 刷新token
	 * 
	 * @param token
	 * @return
	 */
	public static String refreshToken(String token) {
		String refreshedToken;
		try {
			final JSONObject payload = JwtUtil.getPayload(token);
			payload.put(JwtUtil.CLAIMS_IAT, generateCurrentDate());
			payload.put(JwtUtil.CLAIMS_EXP, generateExpirationDate());
			refreshedToken = JwtUtil.encode(payload.toString());
		} catch (Exception e) {
			LOGGER.error("刷新Token失败！", e);
			refreshedToken = null;
		}
		return refreshedToken;
	}

	private static Date generateCurrentDate() {
		return DateUtil.now();
	}

	private static Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + PolarisConstants.JWT_EXPIRATION);
	}

	private static String generateAudience(Device device) {
		String audience = DeviceEnum.Unknown.name();
		if (device.isNormal()) {
			audience = DeviceEnum.PC.name();
		} else if (device.isTablet()) {
			audience = DeviceEnum.Tablet.name();
		} else if (device.isMobile()) {
			audience = DeviceEnum.Mobile.name();
		}
		return audience;
	}
	
	/**
	 * 校验token是否合法
	 * 
	 * @param token
	 * @param userDetails
	 * @return
	 */
	public static Boolean validateToken(String token, UserDetails userDetails) {
		SecurityUser securityUser = (SecurityUser) userDetails;
		final String userId = getUserIdFromToken(token);
		final String username = getUsernameFromToken(token);
		final Date createTime = getCreateTimeFromToken(token);
		final Date expiration = getExpirationDateFromToken(token);
		boolean subResult1 = StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(username) && createTime != null
				&& expiration != null;
		if (subResult1) {
			boolean subResult2 = username.equals(securityUser.getUser().getUsername())
					&& userId.equals(securityUser.getUser().getId());
			boolean subResult3 = JwtUtil.verifyJwtToken(token);
			boolean subResult4 = !isTokenExpired(token);
			boolean subResult5 = !isCreateTimeBeforeLastPasswordResetTime(createTime,
					new Date(securityUser.getUser().getLastPasswordResetTime().getTime()));
			return subResult2 && subResult3 && subResult4 && subResult5;
		} else {
			return false;
		}
	}

	/**
	 * 从JWT中获取username
	 * 
	 * @param token
	 * @return
	 */
	public static String getUsernameFromToken(String token) {
		String username;
		try {
			final JSONObject payload = JwtUtil.getPayload(token);
			username = payload.getString(JwtUtil.CLAIMS_USERNAME);
		} catch (Exception e) {
			LOGGER.info(MessageFormat.format(errMsg, token, "username"));
			username = null;
		}
		return username;
	}

	/**
	 * 从JWT中获取roles字符串拼接
	 * 
	 * @param token
	 * @return
	 */
	public static String getRolesFromToken(String token) {
		String roles;
		try {
			final JSONObject payload = JwtUtil.getPayload(token);
			roles = payload.getString(JwtUtil.CLAIMS_ROLES);
		} catch (Exception e) {
			LOGGER.info(MessageFormat.format(errMsg, token, "roles"));
			roles = null;
		}
		return roles;
	}

	/**
	 * 从JWT中获取userId
	 * 
	 * @param token
	 * @return
	 */
	public static String getUserIdFromToken(String token) {
		String userId;
		try {
			final JSONObject payload = JwtUtil.getPayload(token);
			userId = payload.getString(JwtUtil.CLAIMS_SUB);
		} catch (Exception e) {
			LOGGER.info(MessageFormat.format(errMsg, token, "userId"));
			userId = null;
		}
		return userId;
	}

	/**
	 * 从JWT中获取token的签发创建时间
	 * 
	 * @param token
	 * @return
	 */
	public static Date getCreateTimeFromToken(String token) {
		Date createTime;
		try {
			final JSONObject payload = JwtUtil.getPayload(token);
			createTime = new Date(payload.getLong(JwtUtil.CLAIMS_IAT));
		} catch (Exception e) {
			LOGGER.info(MessageFormat.format(errMsg, token, "createTime"));
			createTime = null;
		}
		return createTime;
	}

	/**
	 * 从JWT中获取token的过期时间
	 * 
	 * @param token
	 * @return
	 */
	public static Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			final JSONObject payload = JwtUtil.getPayload(token);
			expiration = new Date(payload.getLong(JwtUtil.CLAIMS_EXP));
		} catch (Exception e) {
			LOGGER.info(MessageFormat.format(errMsg, token, "expiration"));
			expiration = null;
		}
		return expiration;
	}

	/**
	 * 从JWT中获取发送请求的设备类型
	 * 
	 * @param token
	 * @return
	 */
	public static String getAudienceFromToken(String token) {
		String audience;
		try {
			final JSONObject payload = JwtUtil.getPayload(token);
			audience = payload.getString(JwtUtil.CLAIMS_AUD);
		} catch (Exception e) {
			LOGGER.info(MessageFormat.format(errMsg, token, "audience"));
			audience = null;
		}
		return audience;
	}

	// 生成用户角色字符串拼接
	private static String generateRoles(SecurityUser securityUser) {
		Collection<? extends GrantedAuthority> authorities = securityUser.getAuthorities();
		List<String> roles = new ArrayList<>();
		for (GrantedAuthority authority : authorities) {
			if (!roles.contains(authority.getAuthority())) {
				roles.add(authority.getAuthority());
			}
		}
		return StringUtils.join(roles, ",");
	}

	// 如果是移动设备的话不考虑token过期的问题，即使没有过期，也可以刷新
	private static Boolean ignoreTokenExpiration(String token) {
		String audience = getAudienceFromToken(token);
		return (DeviceEnum.Tablet.name().equals(audience) || DeviceEnum.Mobile.name().equals(audience));
	}

	// 判断token是否过期
	private static Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		if (expiration == null) {
			return true;
		}
		return expiration.before(generateCurrentDate());
	}

	// 判断是否是在密码重置之前创建的，如果是的话，需要判定为失效token，用户需要重新登录
	private static Boolean isCreateTimeBeforeLastPasswordResetTime(Date createTime, Date lastPasswordReset) {
		return (lastPasswordReset != null && createTime.before(lastPasswordReset));
	}

}
