package com.orion.common.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private static final Logger LOG = LogManager.getLogger(RestAuthenticationSuccessHandler.class);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		LOG.info("Auth success!");
		clearAuthenticationAttributes(request);
	}
}
