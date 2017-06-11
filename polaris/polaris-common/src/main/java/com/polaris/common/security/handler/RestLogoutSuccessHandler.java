package com.polaris.common.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class RestLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

	private static final Logger LOG = LogManager.getLogger(RestLogoutSuccessHandler.class);

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		LOG.info("Logout success!");
	}
}
