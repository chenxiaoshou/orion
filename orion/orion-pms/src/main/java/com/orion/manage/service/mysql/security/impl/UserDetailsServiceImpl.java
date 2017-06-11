package com.orion.manage.service.mysql.security.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.orion.manage.model.mysql.auth.User;
import com.orion.manage.model.mysql.security.SecurityUser;
import com.orion.manage.service.mysql.auth.UserService;
import com.orion.manage.service.mysql.security.SecurityService;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userService.findByUsernameAndEnableTrueAndLockedFalse(username);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		} else {
			List<SimpleGrantedAuthority> authorities = securityService.getAuthorities(user.getId());
			return new SecurityUser(user, authorities);
		}
	}

}
