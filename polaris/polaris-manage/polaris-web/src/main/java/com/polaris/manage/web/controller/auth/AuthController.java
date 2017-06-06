package com.polaris.manage.web.controller.auth;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mobile.device.Device;
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

import com.polaris.common.exception.ApiException;
import com.polaris.common.utils.BeanUtil;
import com.polaris.manage.service.dto.component.UserInfoCache;
import com.polaris.manage.service.srv.component.RedisService;
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

	/**
	 * 登录认证
	 * 
	 * @param auth4Login
	 * @param device
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public AuthInfo login(@RequestBody @Valid Auth4Login auth4Login, Device device, HttpServletRequest request) {
		// 调用spring security认证逻辑
		Authentication authentication = this.authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(auth4Login.getUsername(), auth4Login.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// 如果认证通过，这里为其生成token
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(auth4Login.getUsername());
		String token = TokenUtil.generateToken(userDetails, device);

		LOGGER.info("user [" + auth4Login.getUsername() + "] login successful, token [" + token + "]");

		// 将token以及用户相关信息保存在redis中
		SecurityUser securityUser = (SecurityUser) userDetails;
		if (securityUser.getUser() == null) {
			throw new ApiException("auth.login_failed");
		}
		UserInfoCache userInfoCache = new UserInfoCache();
		BeanUtil.copyProperties(securityUser.getUser(), userInfoCache);
		this.redisService.storeUserInfo(token, userInfoCache);

		// 将token返回给前端
		AuthInfo authInfo = new AuthInfo();
		authInfo.setToken(token);
		return authInfo;
	}
	
	// TODO 登出操作

}
