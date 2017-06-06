package com.polaris.security.factory;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.polaris.manage.model.mysql.auth.User;
import com.polaris.security.model.SecurityUser;

public final class SecurityUserFactory {

	private SecurityUserFactory() {
	}

	public static SecurityUser create(User user, List<SimpleGrantedAuthority> authorities) {
		return new SecurityUser(user, authorities);
	}

}
