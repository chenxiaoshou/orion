package com.orion.manage.service.mysql.security.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.orion.common.dic.SourceTypeEnum;
import com.orion.common.utils.BeanUtil;
import com.orion.manage.model.mysql.auth.MapUserRole;
import com.orion.manage.model.mysql.auth.Role;
import com.orion.manage.model.mysql.auth.User;
import com.orion.manage.service.dto.component.UserInfoCache;
import com.orion.manage.service.mysql.auth.MapUserRoleService;
import com.orion.manage.service.mysql.auth.RoleService;
import com.orion.manage.service.mysql.auth.UserService;
import com.orion.manage.service.mysql.component.RedisService;
import com.orion.manage.service.mysql.security.SecurityService;
import com.orion.manage.service.mysql.security.TokenService;

@Service("securityService")
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	private RoleService roleService;

	@Autowired
	private MapUserRoleService mapUserRoleService;

	@Autowired
	private RedisService redisService;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UserService userService;

	@Override
	public List<SimpleGrantedAuthority> getAuthorities(String userId) {
		List<MapUserRole> mapUserRoles = mapUserRoleService.findByUserId(userId);
		List<String> roleIds = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(mapUserRoles)) {
			for (MapUserRole map : mapUserRoles) {
				roleIds.add(map.getRoleId());
			}
		}
		List<Role> roles = roleService.findByIdIn(roleIds);
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(roles)) {
			for (Role role : roles) {
				authorities.add(new SimpleGrantedAuthority(role.getName()));
			}
		}
		return authorities;
	}

	@Override
	public void refreshRedisToken(SourceTypeEnum source, String userId, String oldToken, String newToken) {
		LocalDateTime timeout = this.tokenService.getExpirationDateFromToken(newToken);
		String oldToken2 = this.redisService.storeUserIdToken(source, userId, newToken, timeout);
		UserInfoCache userInfoCache = null;
		if (StringUtils.isNotBlank(oldToken2)) {
			userInfoCache = this.redisService.removeTokenUserInfo(source, oldToken2);
		}
		// 如果有垃圾数据，剔除掉
		if (StringUtils.isNotBlank(oldToken) && !oldToken.equals(oldToken2)) {
			this.redisService.removeTokenUserInfo(source, oldToken);
		}
		if (userInfoCache == null) {
			User user = this.userService.find(userId);
			BeanUtil.copyProperties(user, userInfoCache);
		}
		LocalDateTime expiration = this.tokenService.getExpirationDateFromToken(newToken);
		this.redisService.storeTokenUserInfo(source, newToken, userInfoCache, expiration);
	}

}
