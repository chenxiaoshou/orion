package com.polaris.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.polaris.manage.model.mysql.auth.MapUserRole;
import com.polaris.manage.model.mysql.auth.Role;
import com.polaris.manage.service.mysql.auth.MapUserRoleService;
import com.polaris.manage.service.mysql.auth.RoleService;
import com.polaris.security.service.SecurityService;

@Service("securityService")
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	private RoleService roleService;

	@Autowired
	private MapUserRoleService mapUserRoleService;

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

}
