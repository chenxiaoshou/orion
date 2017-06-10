package com.polaris.security.util;

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
import org.springframework.mobile.device.Device;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.polaris.common.constant.PolarisConstants;
import com.polaris.common.utils.JwtUtil;
import com.polaris.common.utils.RSAUtil;
import com.polaris.manage.web.vo.auth.AuthInfo;
import com.polaris.security.model.SecurityUser;

import net.sf.json.JSONObject;

public class TokenUtil {

	private static final Logger LOGGER = LogManager.getLogger(TokenUtil.class);

	private static String errMsg = "Parse {0} from JWT [{2}] failure!";

	private TokenUtil() {
	}

	/**
	 * 生成jwt
	 * 
	 * @param userDetails
	 *            用户信息
	 * @param tokenSigner
	 *            token的签发者
	 * @param remoteHost
	 *            客户端IP
	 * @param device
	 *            客户端设备
	 * @return
	 */
	public static String generateToken(UserDetails userDetails, String tokenSigner, String remoteHost, Device device) {
		Map<String, Object> payloads = JwtUtil.buildNormalJwtPayloads();
		SecurityUser securityUser = (SecurityUser) userDetails;
		payloads.put(JwtUtil.CLAIMS_ISS, tokenSigner);
		payloads.put(JwtUtil.CLAIMS_SUB, securityUser.getUser().getId());
		payloads.put(JwtUtil.CLAIMS_AUD, remoteHost);
		payloads.put(JwtUtil.CLAIMS_USERNAME, securityUser.getUser().getUsername());
		payloads.put(JwtUtil.CLAIMS_DEVICE, generateClientDevice(device));
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
	 *            用户信息
	 * @param tokenSigner
	 *            token的签发者
	 * @param remoteHost
	 *            客户端IP
	 * @param device
	 *            客户端设备
	 * @return
	 */
	public static AuthInfo generateTokenAndBuildAuthInfo(UserDetails userDetails, String tokenSigner, String remoteHost,
			Device device) {
		Map<String, Object> payloads = JwtUtil.buildNormalJwtPayloads();
		SecurityUser securityUser = (SecurityUser) userDetails;
		payloads.put(JwtUtil.CLAIMS_ISS, tokenSigner);
		payloads.put(JwtUtil.CLAIMS_SUB, securityUser.getUser().getId());
		payloads.put(JwtUtil.CLAIMS_AUD, remoteHost);
		payloads.put(JwtUtil.CLAIMS_USERNAME, securityUser.getUser().getUsername());
		payloads.put(JwtUtil.CLAIMS_DEVICE, generateClientDevice(device));
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
		authInfo.setDevice(DeviceEnum.valueOf(String.valueOf(payloads.get(JwtUtil.CLAIMS_DEVICE))));
		authInfo.setExpiration((Long) payloads.get(JwtUtil.CLAIMS_EXP));
		authInfo.setPublicKey(RSAUtil.getBase64PublicKey());
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
	 *            用户最后一次密码重置的时间
	 * @return
	 */
	public static Boolean canTokenBeRefreshed(String token, LocalDateTime lastPasswordResetTime) {
		final LocalDateTime createTime = getCreateTimeFromToken(token);
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
			long createTime = Instant.now().toEpochMilli();
			payload.put(JwtUtil.CLAIMS_IAT, createTime);
			payload.put(JwtUtil.CLAIMS_EXP, createTime + PolarisConstants.JWT_EXPIRATION);
			refreshedToken = JwtUtil.encode(payload.toString());
		} catch (Exception e) {
			LOGGER.error("刷新Token失败！", e);
			refreshedToken = null;
		}
		AuthInfo authInfo = new AuthInfo();
		if (payload != null) {
			authInfo.setCreateTime(payload.getLong(JwtUtil.CLAIMS_IAT));
			authInfo.setDevice(DeviceEnum.valueOf(String.valueOf(payload.get(JwtUtil.CLAIMS_DEVICE))));
			authInfo.setExpiration(payload.getLong(JwtUtil.CLAIMS_EXP));
			authInfo.setPublicKey(RSAUtil.getBase64PublicKey());
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

	private static LocalDateTime generateCurrentDate() {
		return LocalDateTime.now();
	}

	private static LocalDateTime generateExpirationDate() {
		return LocalDateTime.ofInstant(
				Instant.ofEpochMilli(System.currentTimeMillis() + PolarisConstants.JWT_EXPIRATION),
				ZoneId.systemDefault());
	}

	private static String generateClientDevice(Device device) {
		String clientDevice = DeviceEnum.Unknown.name();
		if (device.isNormal()) {
			clientDevice = DeviceEnum.PC.name();
		} else if (device.isTablet()) {
			clientDevice = DeviceEnum.Tablet.name();
		} else if (device.isMobile()) {
			clientDevice = DeviceEnum.Mobile.name();
		}
		return clientDevice;
	}

	/**
	 * 校验token是否合法
	 * 
	 * @param token
	 * @param userDetails
	 * @return
	 */
	public static Boolean isTokenAvailable(String token, UserDetails userDetails) {
		SecurityUser securityUser = (SecurityUser) userDetails;
		final String userId = getUserIdFromToken(token);
		final String username = getUsernameFromToken(token);
		final LocalDateTime createTime = getCreateTimeFromToken(token);
		final LocalDateTime expiration = getExpirationDateFromToken(token);
		boolean subResult1 = StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(username) && createTime != null
				&& expiration != null;
		if (subResult1) {
			boolean subResult2 = username.equals(securityUser.getUser().getUsername())
					&& userId.equals(securityUser.getUser().getId());
			boolean subResult3 = JwtUtil.verifyJwtToken(token);
			if (!subResult3 && LOGGER.isDebugEnabled()) {
				LOGGER.debug("username [" + username + "] token is illegal.");
			}
			boolean subResult4 = !isTokenExpired(token);
			if (!subResult4 && LOGGER.isDebugEnabled()) {
				LOGGER.debug("username [" + username + "] token is expired.");
			}
			boolean subResult5 = true;
			if (securityUser.getUser().getLastPasswordResetTime() != null) {
				subResult5 = !isCreateTimeBeforeLastPasswordResetTime(createTime,
						securityUser.getUser().getLastPasswordResetTime());
				if (!subResult5 && LOGGER.isDebugEnabled()) {
					LOGGER.debug("username [" + username + "] create time before last password reset time.");
				}
			}
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
		String username = null;
		try {
			if (StringUtils.isNotBlank(token)) {
				final JSONObject payload = JwtUtil.getPayload(token);
				username = payload.getString(JwtUtil.CLAIMS_USERNAME);
			}
		} catch (Exception e) {
			LOGGER.debug(MessageFormat.format(errMsg, "username", token));
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
		String roles = null;
		try {
			if (StringUtils.isNotBlank(token)) {
				final JSONObject payload = JwtUtil.getPayload(token);
				roles = payload.getString(JwtUtil.CLAIMS_ROLES);
			}
		} catch (Exception e) {
			LOGGER.debug(MessageFormat.format(errMsg, "roles", token));
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
		String userId = null;
		try {
			if (StringUtils.isNotBlank(token)) {
				final JSONObject payload = JwtUtil.getPayload(token);
				userId = payload.getString(JwtUtil.CLAIMS_SUB);
			}
		} catch (Exception e) {
			LOGGER.debug(MessageFormat.format(errMsg, "userId", token));
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
	public static LocalDateTime getCreateTimeFromToken(String token) {
		LocalDateTime createTime = null;
		try {
			if (StringUtils.isNotBlank(token)) {
				final JSONObject payload = JwtUtil.getPayload(token);
				createTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(payload.getLong(JwtUtil.CLAIMS_IAT)),
						ZoneId.systemDefault());
			}
		} catch (Exception e) {
			LOGGER.debug(MessageFormat.format(errMsg, "createTime", token));
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
	public static LocalDateTime getExpirationDateFromToken(String token) {
		LocalDateTime expiration = null;
		try {
			if (StringUtils.isNotBlank(token)) {
				final JSONObject payload = JwtUtil.getPayload(token);
				expiration = LocalDateTime.ofInstant(Instant.ofEpochMilli(payload.getLong(JwtUtil.CLAIMS_EXP)),
						ZoneId.systemDefault());
			}
		} catch (Exception e) {
			LOGGER.debug(MessageFormat.format(errMsg, "expiration", token));
			expiration = null;
		}
		return expiration;
	}

	/**
	 * 从JWT中获取发送请求客户端的Host
	 * 
	 * @param token
	 * @return
	 */
	public static String getRemoteHostFromToken(String token) {
		String remoteHost = null;
		try {
			if (StringUtils.isNotBlank(token)) {
				final JSONObject payload = JwtUtil.getPayload(token);
				remoteHost = payload.getString(JwtUtil.CLAIMS_AUD);
			}
		} catch (Exception e) {
			LOGGER.debug(MessageFormat.format(errMsg, "remoteHost", token));
			remoteHost = null;
		}
		return remoteHost;
	}

	private static String getDeviceFromToken(String token) {
		String device = null;
		try {
			if (StringUtils.isNotBlank(token)) {
				final JSONObject payload = JwtUtil.getPayload(token);
				device = payload.getString(JwtUtil.CLAIMS_DEVICE);
			}
		} catch (Exception e) {
			LOGGER.debug(MessageFormat.format(errMsg, "device", token));
			device = null;
		}
		return device;
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
		String device = getDeviceFromToken(token);
		return (DeviceEnum.Tablet.name().equals(device) || DeviceEnum.Mobile.name().equals(device));
	}

	// 判断token是否过期
	private static Boolean isTokenExpired(String token) {
		final LocalDateTime expiration = getExpirationDateFromToken(token);
		if (expiration == null) {
			return true;
		}
		return !expiration.isAfter(generateCurrentDate());
	}

	// 判断是否是在密码重置之前创建的，如果是的话，需要判定为失效token，用户需要重新登录
	private static Boolean isCreateTimeBeforeLastPasswordResetTime(LocalDateTime createTime,
			LocalDateTime lastPasswordReset) {
		return (lastPasswordReset != null && !createTime.isAfter(lastPasswordReset));
	}

}
