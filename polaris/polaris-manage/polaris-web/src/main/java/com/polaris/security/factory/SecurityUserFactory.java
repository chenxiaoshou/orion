package com.polaris.security.factory;

import com.polaris.manage.model.mysql.auth.User;
import com.polaris.security.model.SecurityUser;

public class SecurityUserFactory {

	public static SecurityUser create(User user) {
		return new SecurityUser(user);
	}

}
