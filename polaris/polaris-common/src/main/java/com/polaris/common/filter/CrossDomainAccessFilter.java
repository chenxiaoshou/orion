package com.polaris.common.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 解决Ajax跨域请求问题的过滤器
 * 
 * @author John
 *
 */
public class CrossDomainAccessFilter extends OncePerRequestFilter {

	@Value("${response.header.allowCrosAccess}")
	private boolean allowCrosAccess;

	@Value("${response.header.accessControlAllowOrigin}")
	private String allowOriginUrl;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 允许跨域访问
		if (allowCrosAccess) {
			response.setHeader("Access-Control-Allow-Origin", allowOriginUrl);
			response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
			// 允许传出去的消息头，如果有自定义的token之类的字段，也需要添加进来,不然前台永远不可能获取到
			response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
			response.addHeader("Access-Control-Max-Age", "1800"); // 30 min
			// response.setHeader("Access-Control-Allow-Credentials", "true");
		}
		// 不要缓存页面
		response.addHeader(HttpHeaders.EXPIRES, "-1");
		response.addHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
		response.addHeader(HttpHeaders.PRAGMA, "no-cache");
		filterChain.doFilter(request, response);
	}

}
