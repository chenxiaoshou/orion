package com.orion.security.filter;

import java.io.IOException;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.orion.manage.model.mysql.auth.User;
import com.orion.manage.model.mysql.security.SecurityUser;
import com.orion.manage.service.mysql.component.RedisService;
import com.orion.manage.service.mysql.security.SecurityService;
import com.orion.manage.service.mysql.security.TokenService;

public class AuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

	private static final Logger LOG = LogManager.getLogger(AuthenticationTokenFilter.class);

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private RedisService redisService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private TokenService tokenService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authToken = httpRequest.getHeader(SecurityConstants.HEADER_AUTH_TOKEN);
		// 检查在redis服务器中是否存有该用户的信息
		if (StringUtils.isNotBlank(authToken) && existsInRedis(authToken)) {
			String username = this.tokenService.getUsernameFromToken(authToken);
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("Checking authentication for user [" + username + "] token [" + authToken + "]");
				}
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
				// 如果验证token无误，视为认证通过
				if (this.tokenService.isTokenAvailable(authToken, userDetails)) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
					SecurityContextHolder.getContext().setAuthentication(authentication);
					// 检查token是否快到过期时间，如果快到的话，自动刷新token；
					checkRefreshToken(authToken, userDetails, response);
				}
			}
		}

		chain.doFilter(request, response);
	}

	private void checkRefreshToken(String authToken, UserDetails userDetails, ServletResponse response) {
		SecurityUser securityUser = (SecurityUser) userDetails;
		User user = securityUser.getUser();
		boolean needRefresh = this.tokenService.canTokenBeRefreshed(authToken, user.getLastPasswordResetTime());
		if (needRefresh) { // 需要刷新
			String newToken = this.tokenService.refreshToken(authToken);
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			httpServletResponse.addHeader(SecurityConstants.HEADER_NEW_AUTH_TOKEN, newToken);
			LOG.debug("Refresh token for user [" + user.getUsername() + "] newToken [" + newToken + "]");
			// 刷新redis中的保存的用户会话和信息
			this.securityService.refreshRedisToken(user.getId(), authToken, newToken);
		}

	}

	/**
	 * 
	 * @param userDetails
	 * @return
	 * @throws AuthenticationException
	 */
	private boolean existsInRedis(String authToken) throws AuthenticationException {
		String userId = this.tokenService.getUserIdFromToken(authToken);
		String tokenInRedis = redisService.getUserIdToken(userId);
		boolean existsInRedis = StringUtils.isNoneBlank(tokenInRedis) && tokenInRedis.equalsIgnoreCase(authToken)
				&& this.redisService.getTokenUserInfo(authToken) != null;
		if (LOG.isDebugEnabled() && !existsInRedis) {
			LOG.debug("Token not exists in redis!");
		}
		return existsInRedis;
	}

}
