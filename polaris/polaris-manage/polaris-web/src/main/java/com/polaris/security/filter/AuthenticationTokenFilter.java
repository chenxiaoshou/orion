package com.polaris.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.polaris.common.constant.PolarisConstants;
import com.polaris.manage.service.mysql.component.RedisService;
import com.polaris.security.util.TokenUtil;

public class AuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

	private static final Logger LOG = LogManager.getLogger(AuthenticationTokenFilter.class);

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private RedisService redisService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authToken = httpRequest.getHeader(PolarisConstants.HEADER_AUTH_TOKEN);

		if (StringUtils.isNoneBlank(authToken)) {
			String username = TokenUtil.getUsernameFromToken(authToken);
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("Checking authentication for user [" + username + "] token [" + authToken + "]");
				}
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
				// 如果验证token无误，并且在redis服务器中存有相关用户信息的话，视为认证通过
				if (TokenUtil.isTokenAvailable(authToken, userDetails) && inRedis(authToken)) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		}

		chain.doFilter(request, response);
	}

	/**
	 * 
	 * @param userDetails
	 * @return
	 */
	private boolean inRedis(String authToken) {
		return redisService.getUserInfo(authToken) != null;
	}

}
