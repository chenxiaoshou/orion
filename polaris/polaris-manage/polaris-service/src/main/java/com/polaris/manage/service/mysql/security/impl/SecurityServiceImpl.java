package com.polaris.manage.service.mysql.security.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.polaris.manage.model.mysql.auth.MapUserRole;
import com.polaris.manage.model.mysql.auth.Role;
import com.polaris.manage.service.dto.component.UserInfoCache;
import com.polaris.manage.service.mysql.auth.MapUserRoleService;
import com.polaris.manage.service.mysql.auth.RoleService;
import com.polaris.manage.service.mysql.component.RedisService;
import com.polaris.manage.service.mysql.security.SecurityService;
import com.polaris.manage.service.mysql.security.TokenService;

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
	public void refreshRedisToken(String userId, String oldToken, String newToken) {
		String token = this.redisService.getUserIdToken(userId);
		UserInfoCache userInfoCache = this.redisService.getTokenUserInfo(token);
		if (StringUtils.isNotBlank(token) && userInfoCache != null) {
			this.redisService.removeUserIdToken(userId);
			if (!token.equalsIgnoreCase(oldToken)) { // 检查垃圾数据，如果存在，删除掉
				String garbageDataUserId = this.tokenService.getUserIdFromToken(oldToken);
				this.redisService.removeUserIdToken(garbageDataUserId);
			}

			// 存入新的
			this.redisService.storeUserIdToken(userId, token);
			LocalDateTime expiration = this.tokenService.getExpirationDateFromToken(newToken);
			this.redisService.storeTokenUserInfo(token, userInfoCache, expiration);
		}
	}

}
