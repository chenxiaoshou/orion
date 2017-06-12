package com.orion.manage.web.controller.auth;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.orion.common.constant.SecurityConstants;
import com.orion.common.exception.ApiException;
import com.orion.common.utils.BeanUtil;
import com.orion.common.utils.JwtUtil;
import com.orion.common.utils.RSAUtil;
import com.orion.manage.model.mysql.auth.User;
import com.orion.manage.model.mysql.security.SecurityUser;
import com.orion.manage.service.dto.component.UserInfoCache;
import com.orion.manage.service.mysql.auth.UserService;
import com.orion.manage.service.mysql.component.RedisService;
import com.orion.manage.service.mysql.security.SecurityService;
import com.orion.manage.service.mysql.security.TokenService;
import com.orion.manage.web.controller.BaseController;
import com.orion.manage.web.vo.auth.Auth4Login;
import com.orion.manage.web.vo.auth.AuthInfo;
import com.orion.security.DeviceEnum;

import net.sf.json.JSONObject;

@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {

	private static final Logger LOGGER = LogManager.getLogger(AuthController.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private RedisService redisService;

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private TokenService tokenService;

	/**
	 * 登录认证
	 * 
	 * @param auth4Login
	 * @param device
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public AuthInfo login(@RequestBody @Valid Auth4Login auth4Login, HttpServletRequest request) {
		Device device = DeviceUtils.getCurrentDevice(request);
		// 调用spring security认证逻辑
		Authentication authentication = this.authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(auth4Login.getUsername(), auth4Login.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// 如果认证通过，这里为其生成token
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(auth4Login.getUsername());
		SecurityUser securityUser = (SecurityUser) userDetails;
		if (securityUser.getUser() == null) {
			throw new ApiException("auth.login_failed");
		}
		User user = securityUser.getUser();
		String remoteHost = getRemoteHost(request);
		String token = this.tokenService.generateToken(userDetails, remoteHost, request.getRequestURL().toString(),
				device);
		LOGGER.info("user [" + user.getUsername() + "] login successful, token [" + token + "]");

		// 将用户id和token对应关系保存在Redis中，
		// 做到一个用户只保留一份合法有效的token，其他token虽然jwt那边验证可以通过，但是在redis中如果不存在的话，也会判定为非法
		LocalDateTime expiration = this.tokenService.getExpirationDateFromToken(token);
		this.redisService.storeUserIdTokenAndClearOldTokenUserInfo(user.getId(), token, expiration);

		// 将token:userinfo以顶级key-value结构表保存到redis中，并设置过期时间
		UserInfoCache userInfoCache = new UserInfoCache();
		BeanUtil.copyProperties(user, userInfoCache);
		this.redisService.storeTokenUserInfo(token, userInfoCache, expiration);

		// 更新数据库User表的最后登录时间
		user.setLastLoginTime(LocalDateTime.now());
		this.userService.modify(securityUser.getUser());

		// 将token返回给前端
		return buildAuthInfo(token);
	}

	/**
	 * 刷新Token
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/refresh", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.CREATED)
	public AuthInfo refresh(HttpServletRequest request) {
		String oldToken = request.getHeader(SecurityConstants.HEADER_AUTH_TOKEN);
		String username = this.tokenService.getUsernameFromToken(oldToken);
		String userId = this.tokenService.getUserIdFromToken(oldToken);
		User user = this.userService.find(userId);
		boolean canBeRefreshedToken = this.tokenService.canTokenBeRefreshed(oldToken, user.getLastPasswordResetTime());
		if (!canBeRefreshedToken) {
			throw new ApiException("auth.cannot_refresh_token");
		}
		String newToken = this.tokenService.refreshToken(oldToken);
		LOGGER.debug("username [" + username + "] refresh token successful!");
		// refresh redis中的用户会话和用户信息
		this.securityService.refreshRedisToken(userId, oldToken, newToken);
		return buildAuthInfo(newToken);
	}

	/**
	 * 根据token构建authinfo
	 * 
	 * @param newToken
	 * @return
	 */
	private AuthInfo buildAuthInfo(String token) {
		JSONObject payload = JwtUtil.getPayload(token);
		AuthInfo authInfo = new AuthInfo();
		if (payload != null) {
			authInfo.setCreateTime(payload.getLong(JwtUtil.CLAIMS_IAT));
			authInfo.setDevice(DeviceEnum.valueOf(String.valueOf(payload.get(JwtUtil.CLAIMS_DEVICE))));
			authInfo.setExpiration(payload.getLong(JwtUtil.CLAIMS_EXP));
			authInfo.setPublicKey(RSAUtil.getBase64PublicKey());
			authInfo.setToken(token);
			authInfo.setUserId(payload.getString(JwtUtil.CLAIMS_SUB));
			authInfo.setUsername(payload.getString(JwtUtil.CLAIMS_USERNAME));
			authInfo.setRoles(payload.getString(JwtUtil.CLAIMS_ROLES));
		}
		return authInfo;
	}

	/**
	 * 登出操作
	 * 
	 * @param auth4Login
	 * @param device
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public void logout(Device device, HttpServletRequest request) {
		// 根据token获取用户的信息
		String token = request.getHeader(SecurityConstants.HEADER_AUTH_TOKEN);
		String userName = this.tokenService.getUsernameFromToken(token);
		String userId = this.tokenService.getUserIdFromToken(token);
		// 删除userIdToken
		this.redisService.removeUserIdToken(userId);
		// 将token以及用户相关信息从redis中删除
		this.redisService.removeTokenUserInfo(token);
		LOGGER.info("userName [" + userName + "] logout successful!");
	}

}
