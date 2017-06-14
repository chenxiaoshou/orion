package com.orion.manage.web.controller.auth;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.orion.common.constant.SecurityConstants;
import com.orion.common.dic.SourceTypeEnum;
import com.orion.common.exception.ApiException;
import com.orion.common.utils.BeanUtil;
import com.orion.common.utils.JwtUtil;
import com.orion.common.utils.RSAUtil;
import com.orion.manage.model.mysql.auth.MapUserRole;
import com.orion.manage.model.mysql.auth.Role;
import com.orion.manage.model.mysql.auth.User;
import com.orion.manage.model.mysql.security.SecurityUser;
import com.orion.manage.service.dto.component.UserInfoCache;
import com.orion.manage.service.mysql.auth.MapUserRoleService;
import com.orion.manage.service.mysql.auth.RoleService;
import com.orion.manage.service.mysql.auth.UserService;
import com.orion.manage.service.mysql.component.RedisService;
import com.orion.manage.service.mysql.security.SecurityService;
import com.orion.manage.service.mysql.security.TokenService;
import com.orion.manage.web.controller.BaseController;
import com.orion.manage.web.vo.auth.Auth4Login;
import com.orion.manage.web.vo.auth.Auth4Register;
import com.orion.manage.web.vo.auth.AuthInfo;

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

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleService roleService;

	@Autowired
	private MapUserRoleService mapUserRoleService;

	/**
	 * 注册
	 * 
	 * @param auth4Register
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public AuthInfo register(@RequestBody @Valid Auth4Register auth4Register, HttpServletRequest request) {
		SourceTypeEnum sourceTypeEnum = super.getSourceType(request);
		User user = this.userService.findByUsername(auth4Register.getUsername());
		if (user != null) {
			throw new ApiException("auth.username.existed");
		}
		user = new User();
		BeanUtil.copyProperties(auth4Register, user);
		user.setPassword(passwordEncoder.encode(auth4Register.getUsername()));
		User dbUser = this.userService.save(user);
		// 设置用户角色
		setMapUserRole(dbUser);
		// 调用SpringSecurity认证逻辑
		authenticate(dbUser.getUsername(), dbUser.getPassword());
		// 生成token
		UserDetails userDetails = getUserDetails(user, dbUser);
		String token = generateToken(request, sourceTypeEnum, user, userDetails);
		// 保存为source||userid = token的结构，保证一个请求源一个用户只会有一个token是有效的
		LocalDateTime expiration = storeUserIdToken(sourceTypeEnum, user, token);
		// 保存为source||token=userInfo结构，并设置redis的过期时间为token的过期时间
		storeTokenUserInfo(sourceTypeEnum, user, token, expiration);
		// 更新数据库User表的最后登录时间
		updateLastLoginTime((SecurityUser) userDetails, user);
		// 将token返回给前端
		return buildAuthInfo(token);
	}

	private void setMapUserRole(User dbUser) {
		List<String> roles = dbUser.getRoleList();
		if (CollectionUtils.isNotEmpty(roles)) {
			List<Role> roleList = this.roleService.findByEnableTrueAndNameIn(roles);
			if (CollectionUtils.isNotEmpty(roleList)) {
				Set<MapUserRole> mapUserRoles = new HashSet<>();
				for (Role role : roleList) {
					mapUserRoles.add(new MapUserRole(dbUser.getId(), role.getId()));
				}
				this.mapUserRoleService.save(mapUserRoles);
			}
		}
	}

	private String generateToken(HttpServletRequest request, SourceTypeEnum sourceTypeEnum, User user,
			UserDetails userDetails) {
		String tokenSigner = request.getRequestURL().toString();
		String remoteHost = getRemoteHost(request);
		String token = this.tokenService.generateToken(userDetails, tokenSigner, remoteHost, sourceTypeEnum);
		LOGGER.info("user [" + user.getUsername() + "] register successful, token [" + token + "]");
		return token;
	}

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
		SourceTypeEnum sourceTypeEnum = super.getSourceType(request); // 获取请求来源（Desktop/Andriod/IOS/H5）
		authenticate(auth4Login.getUsername(), auth4Login.getPassword()); // 调用SpringSecurity认证逻辑
		// 如果认证通过，这里为其生成token
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(auth4Login.getUsername());
		SecurityUser securityUser = (SecurityUser) userDetails;
		if (securityUser.getUser() == null) {
			throw new ApiException("auth.login_failed");
		}
		User user = securityUser.getUser();
		String token = generateToken(request, sourceTypeEnum, user, userDetails);
		// 保存为source||userid = token的结构，保证一个请求源一个用户只会有一个token是有效的
		LocalDateTime expiration = storeUserIdToken(sourceTypeEnum, user, token);
		// 保存为source||token=userInfo结构，并设置redis的过期时间为token的过期时间
		storeTokenUserInfo(sourceTypeEnum, user, token, expiration);
		// 更新数据库User表的最后登录时间
		updateLastLoginTime(securityUser, user);
		// 将token返回给前端
		return buildAuthInfo(token);
	}

	private UserDetails getUserDetails(User user, User dbUser) {
		List<SimpleGrantedAuthority> authorities = this.securityService.getAuthorities(user.getId());
		return new SecurityUser(dbUser, authorities);
	}

	private void updateLastLoginTime(SecurityUser securityUser, User user) {
		user.setLastLoginTime(LocalDateTime.now());
		this.userService.modify(securityUser.getUser());
	}

	private void storeTokenUserInfo(SourceTypeEnum sourceTypeEnum, User user, String token, LocalDateTime expiration) {
		UserInfoCache userInfoCache = new UserInfoCache();
		BeanUtil.copyProperties(user, userInfoCache);
		this.redisService.storeTokenUserInfo(sourceTypeEnum, token, userInfoCache, expiration);
	}

	private LocalDateTime storeUserIdToken(SourceTypeEnum sourceTypeEnum, User user, String token) {
		LocalDateTime expiration = this.tokenService.getExpirationDateFromToken(token);
		this.redisService.storeUserIdTokenAndClearOldTokenUserInfo(sourceTypeEnum, user.getId(), token, expiration);
		return expiration;
	}

	// 调用spring security认证逻辑
	private void authenticate(String username, String password) {
		Authentication authentication = this.authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	/**
	 * 刷新Token
	 * 
	 * @param request
	 * @return
	 * @throws HttpRequestMethodNotSupportedException
	 */
	@RequestMapping(value = "/refresh", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.CREATED)
	public AuthInfo refresh(HttpServletRequest request) throws HttpRequestMethodNotSupportedException {
		SourceTypeEnum sourceTypeEnum = super.getSourceType(request); // 获取请求来源（Desktop/Andriod/IOS/H5）
		String oldToken = request.getHeader(SecurityConstants.HEADER_AUTH_TOKEN);
		String username = this.tokenService.getUsernameFromToken(oldToken);
		String userId = this.tokenService.getUserIdFromToken(oldToken);
		User user = this.userService.find(userId);
		boolean canBeRefreshedToken = this.tokenService.canTokenBeRefreshed(oldToken, user.getLastPasswordResetTime());
		if (!canBeRefreshedToken) {
			throw new ApiException("auth.cannot_refresh_token");
		}
		String newToken = this.tokenService.refreshToken(oldToken);
		LOGGER.debug("username [" + username + "] refresh token successful, new token [" + newToken + "]");
		// 刷新redis中的用户会话和用户信息
		this.securityService.refreshRedisToken(sourceTypeEnum, userId, oldToken, newToken);
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
			authInfo.setExpiration(payload.getLong(JwtUtil.CLAIMS_EXP));
			authInfo.setRefreshTime(payload.getLong(JwtUtil.CLAIMS_REF));
			authInfo.setPublicKey(RSAUtil.getBase64PublicKey());
			authInfo.setToken(token);
			authInfo.setUserId(payload.getString(JwtUtil.CLAIMS_USERID));
			authInfo.setUsername(payload.getString(JwtUtil.CLAIMS_USERNAME));
			authInfo.setRoles(payload.getString(JwtUtil.CLAIMS_ROLES));
			Integer code = payload.getInt(JwtUtil.CLAIMS_SUB);
			if (code != null) {
				authInfo.setSource(SourceTypeEnum.getSourceTypeByCode(code));
			}
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
	public void logout(HttpServletRequest request) {
		SourceTypeEnum sourceTypeEnum = super.getSourceType(request);
		// 根据token获取用户的信息
		String token = request.getHeader(SecurityConstants.HEADER_AUTH_TOKEN);
		String userName = this.tokenService.getUsernameFromToken(token);
		String userId = this.tokenService.getUserIdFromToken(token);
		// 删除userIdToken
		String removeToken = this.redisService.removeUserIdToken(sourceTypeEnum, userId);
		// 将token以及用户相关信息从redis中删除
		this.redisService.removeTokenUserInfo(sourceTypeEnum, removeToken);
		this.redisService.removeTokenUserInfo(sourceTypeEnum, token);
		LOGGER.info("userName [" + userName + "] logout successful!");
	}

}
