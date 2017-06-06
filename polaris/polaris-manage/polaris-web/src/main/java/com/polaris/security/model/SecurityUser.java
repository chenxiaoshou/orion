package com.polaris.security.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.polaris.common.utils.SpringUtil;
import com.polaris.manage.model.mysql.auth.User;
import com.polaris.security.service.SecurityService;

public class SecurityUser implements UserDetails {

	private static final long serialVersionUID = -8241109321740519037L;

	private User user;

	public SecurityUser(User user) {
		this.user = user;
	}

	public SecurityUser() {

	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		if (this.user != null) {
			List<String> roleList = getRoleList();
			if (CollectionUtils.isNotEmpty(roleList)) {
				for (String roleName : roleList) {
					authorities.add(new SimpleGrantedAuthority(roleName));
				}
			} else {
				SecurityService securityService = (SecurityService) SpringUtil.getBean("securityService");
				authorities = securityService.getAuthorities(this.user.getId());
			}
		}
		return authorities;
	}

	private List<String> getRoleList() {
		List<String> roles = new ArrayList<>();
		if (this.user != null) {
			roles = this.user.getRoleList();
		}
		return roles;
	}

	@Override
	public String getPassword() {
		if (this.user != null) {
			return this.user.getPassword();
		}
		return null;
	}

	@Override
	public String getUsername() {
		if (this.user != null) {
			return this.user.getUsername();
		}
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true; // 当前业务需求中没有账号过期的设置，这里统一设置为未过期
	}

	@Override
	public boolean isAccountNonLocked() {
		if (this.user != null) {
			return !this.user.getLocked();
		}
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true; // 当前业务需求没有此种设计，这里统一设置为未过期
	}

	@Override
	public boolean isEnabled() {
		if (this.user != null) {
			return this.user.getEnable();
		}
		return false;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
