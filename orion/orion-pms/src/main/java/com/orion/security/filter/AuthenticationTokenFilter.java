package com.orion.security.filter;

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

import com.orion.common.constant.SecurityConstants;
import com.orion.common.dic.SourceTypeEnum;
import com.orion.manage.service.mysql.security.TokenService;

public class AuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

	private static final Logger LOG = LogManager.getLogger(AuthenticationTokenFilter.class);

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private TokenService tokenService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		SourceTypeEnum sourceTypeEnum = getSourceType(httpRequest);
		String authToken = httpRequest.getHeader(SecurityConstants.HEADER_AUTH_TOKEN);
		// 检查在redis服务器中是否存有该用户的信息
		if (StringUtils.isNotBlank(authToken)) {
			String username = this.tokenService.getUsernameFromToken(authToken);
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("Checking authentication for user [" + username + "] token [" + authToken + "]");
				}
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
				// 如果验证token无误，视为认证通过
				if (userDetails != null && this.tokenService.isTokenAvailable(sourceTypeEnum, authToken, userDetails)) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		}

		chain.doFilter(request, response);
	}

	private SourceTypeEnum getSourceType(HttpServletRequest request) {
		String sourceStr = request.getHeader(SecurityConstants.HEADER_SOURCE);
		if (StringUtils.isNotBlank(sourceStr)) {
			return SourceTypeEnum.getSourceTypeByCode(Integer.valueOf(sourceStr));
		}
		return null;
	}

}
