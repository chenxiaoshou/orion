package com.polaris.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.polaris.manage.model.mysql.security.SecurityUser;

public class UserAuditorAware implements AuditorAware<String> {

	@Override
	public String getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SecurityUser userDetail = (SecurityUser) authentication.getPrincipal();
		if (userDetail != null && userDetail.getUser() != null) {
			return userDetail.getUser().getId();
		}
		return null;
	}

}
