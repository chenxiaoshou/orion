package com.polaris.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.polaris.common.exception.AuthException;
import com.polaris.manage.model.mysql.auth.User;
import com.polaris.manage.persist.mysql.auth.pub.UserDao;
import com.polaris.security.factory.SecurityUserFactory;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws AuthException {
		User user = this.userDao.findByUsername(username);
		if (user == null) {
			throw new AuthException(String.format("No user found with username '%s'.", username));
		} else {
			return SecurityUserFactory.create(user);
		}
	}

}
