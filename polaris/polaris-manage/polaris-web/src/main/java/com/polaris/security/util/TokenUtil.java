package com.polaris.security.util;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;

import com.polaris.common.constant.PolarisConstants;
import com.polaris.common.utils.DateUtil;
import com.polaris.common.utils.JwtUtil;
import com.polaris.security.model.SecurityUser;

import net.sf.json.JSONObject;

public final class TokenUtil {

	private static final Logger LOGGER = LogManager.getLogger(TokenUtil.class);

	private TokenUtil() {

	}

	/**
	 * 从JWT中获取username
	 * 
	 * @param token
	 * @return
	 */
	public static String getUsernameFromToken(String token) {
		String userId;
		try {
			final JSONObject payload = JwtUtil.getPayload(token);
			userId = payload.getString(JwtUtil.CLAIMS_USERNAME);
		} catch (Exception e) {
			LOGGER.error("从JWT [" + token + "] 中获取userId失败！");
			userId = null;
		}
		return userId;
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
			LOGGER.error("从JWT [" + token + "] 中获取userId失败！");
			userId = null;
		}
		return userId;
	}

	/**
	 * 从JWT中获取过期时间
	 * 
	 * @param token
	 * @return
	 */
	public static Date getCreateTimeFromToken(String token) {
		Date createTime;
		try {
			final JSONObject headers = JwtUtil.getHeader(token);
			createTime = new Date(headers.getLong(JwtUtil.CLAIMS_IAT));
		} catch (Exception e) {
			LOGGER.error("从JWT [" + token + "] 中获取createTime失败！");
			createTime = null;
		}
		return createTime;
	}

	public static Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			final JSONObject headers = JwtUtil.getHeader(token);
			expiration = new Date(headers.getLong(JwtUtil.CLAIMS_EXP));
		} catch (Exception e) {
			LOGGER.error("从JWT [" + token + "] 中获取expiration失败！");
			expiration = null;
		}
		return expiration;
	}

	public static String getAudienceFromToken(String token) {
		String audience;
		try {
			final JSONObject headers = JwtUtil.getHeader(token);
			audience = headers.getString(JwtUtil.CLAIMS_AUD);
		} catch (Exception e) {
			LOGGER.error("从JWT [" + token + "] 中获取audience失败！");
			audience = null;
		}
		return audience;
	}

	private static Date generateCurrentDate() {
		return DateUtil.now();
	}

	private static Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + PolarisConstants.JWT_EXPIRATION);
	}

	// 判断token是否失效
	private static Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		if (expiration == null) {
			return true;
		}
		return expiration.before(generateCurrentDate());
	}

	// 判断是否是在密码重置之前创建的，如果是的话，需要判定为失效token，用户需要重新登录
	private static Boolean isCreateTimeBeforeLastPasswordReset(Date createTime, Date lastPasswordReset) {
		return (lastPasswordReset != null && createTime.before(lastPasswordReset));
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

	// 移动设备不考虑token过期的问题
	private static Boolean ignoreTokenExpiration(String token) {
		String audience = getAudienceFromToken(token);
		return (DeviceEnum.Tablet.name().equals(audience) || DeviceEnum.Mobile.name().equals(audience));
	}

	public static String generateToken(UserDetails userDetails, Device device) {
		Map<String, Object> payloads = JwtUtil.buildNormalJwtPayloads();
		SecurityUser securityUser = (SecurityUser) userDetails;
		payloads.put(JwtUtil.CLAIMS_SUB, securityUser.getUser().getId());
		payloads.put(JwtUtil.CLAIMS_USERNAME, securityUser.getUser().getUsername());
		payloads.put(JwtUtil.CLAIMS_AUD, generateAudience(device));
		String token;
		try {
			token = JwtUtil.encode(payloads);
		} catch (Exception e) {
			LOGGER.error("生成Token失败！", e);
			token = null;
		}
		return token;
	}

	public static Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
		final Date createTime = getCreateTimeFromToken(token);
		return (!(isCreateTimeBeforeLastPasswordReset(createTime, lastPasswordReset))
				&& (!(isTokenExpired(token)) || ignoreTokenExpiration(token)));
	}

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

	/**
	 * 校验token是否合法
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
			boolean subResult5 = !isCreateTimeBeforeLastPasswordReset(createTime,
					new Date(securityUser.getUser().getLastPasswordResetTime().getTime()));
			return subResult2 && subResult3 && subResult4 && subResult5;
		} else {
			return false;
		}
	}

}
