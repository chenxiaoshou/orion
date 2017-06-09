package com.polaris.manage.web.controller.auth;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import com.polaris.common.constant.PolarisConstants;
import com.polaris.common.exception.ApiException;
import com.polaris.common.utils.BeanUtil;
import com.polaris.common.utils.DateUtil;
import com.polaris.manage.model.mysql.auth.User;
import com.polaris.manage.service.dto.component.UserInfoCache;
import com.polaris.manage.service.mysql.auth.UserService;
import com.polaris.manage.service.mysql.component.RedisService;
import com.polaris.manage.web.controller.BaseController;
import com.polaris.manage.web.vo.auth.Auth4Login;
import com.polaris.manage.web.vo.auth.AuthInfo;
import com.polaris.security.model.SecurityUser;
import com.polaris.security.util.TokenUtil;

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
		String remoteHost = getRemoteHost(request);
		AuthInfo authInfo = TokenUtil.generateTokenAndBuildAuthInfo(userDetails, remoteHost,
				request.getRequestURL().toString(), device);

		LOGGER.info("user [" + authInfo.getUsername() + "] login successful, token [" + authInfo.getToken() + "]");

		// 将token以及用户相关信息保存在redis中
		SecurityUser securityUser = (SecurityUser) userDetails;
		if (securityUser.getUser() == null) {
			throw new ApiException("auth.login_failed");
		}
		UserInfoCache userInfoCache = new UserInfoCache();
		BeanUtil.copyProperties(securityUser.getUser(), userInfoCache);
		this.redisService.storeUserInfo(authInfo.getToken(), userInfoCache);

		// 更新数据库User表的最后登录时间
		User user = securityUser.getUser();
		user.setLastLoginTime(DateUtil.timestamp());
		this.userService.modify(securityUser.getUser());

		// 将token返回给前端
		return authInfo;
	}

	/**
	 * 刷新Token
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/refresh", method = RequestMethod.GET)
	public ResponseEntity<AuthInfo> authenticationRequest(HttpServletRequest request) {
		String token = request.getHeader(PolarisConstants.HEADER_AUTH_TOKEN);
		String username = TokenUtil.getUsernameFromToken(token);
		SecurityUser securityUser = (SecurityUser) this.userDetailsService.loadUserByUsername(username);
		// 不符合刷新条件的话，抛出异常
		if (!TokenUtil.canTokenBeRefreshed(token, securityUser.getUser().getLastPasswordResetTime())) {
			throw new ApiException("auth.cannnot_refresh_token");
		}
		AuthInfo authInfo = TokenUtil.refreshTokenAndBuildAuthInfo(token);
		return ResponseEntity.ok(authInfo);
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
		String token = request.getHeader(PolarisConstants.HEADER_AUTH_TOKEN);
		String userName = TokenUtil.getUsernameFromToken(token);
		LOGGER.info("userName [" + userName + "] logout");
		// 将token以及用户相关信息从redis中删除
		this.redisService.removeUserInfo(token);
	}

}
