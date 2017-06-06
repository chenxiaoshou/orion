package com.polaris.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.polaris.common.exception.AuthException;
import com.polaris.manage.model.mysql.auth.User;
import com.polaris.manage.service.srv.auth.UserService;
import com.polaris.security.factory.SecurityUserFactory;
import com.polaris.security.service.SecurityService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@Override
	public UserDetails loadUserByUsername(String username) throws AuthException {
		User user = this.userService.findByUsername(username);
		if (user == null) {
			throw new AuthException(String.format("No user found with username '%s'.", username));
		} else {
			List<SimpleGrantedAuthority> authorities = securityService.getAuthorities(user.getId());
			return SecurityUserFactory.create(user, authorities);
		}
	}

}
