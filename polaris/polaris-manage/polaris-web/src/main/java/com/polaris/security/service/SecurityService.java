package com.polaris.security.service;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public interface SecurityService {

	/**
	 * 获取用户的角色授权列表
	 * @return
	 */
	public List<SimpleGrantedAuthority> getAuthorities(String userId);

}
