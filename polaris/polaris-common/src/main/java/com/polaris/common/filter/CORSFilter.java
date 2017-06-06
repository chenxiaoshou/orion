package com.polaris.common.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.polaris.common.exception.CORSException;
import com.polaris.common.filter.dic.CORSRequestType;

public class CORSFilter implements Filter {

	/**
	 * 跨域访问，服务端要想获取客户端和设置客户端的Cookie，必须要设置具体的域地址，不能使用通配符*
	 */
	public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";

	/**
	 * GET,PUT,POST,DELETE,OPTIONS,HEAD
	 */
	public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";

	/**
	 * 设置为true，允许前端提交在ACCESS_CONTROL_ALLOW_ORIGIN中设置的域下的cookie
	 */
	public static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";

	/**
	 * 允许传进来的消息头，如果有自定义的Token之类的请求头，也需要添加进来,不然后台获取不到值
	 * Origin,X-Requested-With,Content-Type,Accept,Cache-Control
	 */
	public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";

	/**
	 * 允许暴露给浏览器的消息头，自定义消息相应头需要在此处指明
	 * Origin,X-Requested-With,Content-Type,Accept,Cache-Control
	 */
	public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";

	/**
	 * 该字段可选，用来指定本次预检请求的有效期，单位为秒
	 */
	public static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";

	private static final String ORIGIN = "Origin";

	private static final String REFERER = "Referer";

	private static final String HTTP = "http";

	private static final String HTTPS = "https";

	private static final String SLASH = "/";

	private static final String SLASH2 = "//";

	private String allowOrigin;

	private String allowMethods;

	private String allowCredentials;

	private String allowHeaders;

	private String exposeHeaders;

	private String accessControlMaxAge;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.allowOrigin = filterConfig.getInitParameter(ACCESS_CONTROL_ALLOW_ORIGIN);
		this.allowMethods = filterConfig.getInitParameter(ACCESS_CONTROL_ALLOW_METHODS);
		this.allowCredentials = filterConfig.getInitParameter(ACCESS_CONTROL_ALLOW_CREDENTIALS);
		this.allowHeaders = filterConfig.getInitParameter(ACCESS_CONTROL_ALLOW_HEADERS);
		this.exposeHeaders = filterConfig.getInitParameter(ACCESS_CONTROL_EXPOSE_HEADERS);
		this.accessControlMaxAge = filterConfig.getInitParameter(ACCESS_CONTROL_MAX_AGE);
	}

	private void printMessage(CORSException corsException, HttpServletResponse response)
			throws IOException, ServletException {
		response.setStatus(corsException.getHTTPStatusCode());
		response.resetBuffer();
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println("Cross-Origin Resource Sharing (CORS) Filter: " + corsException.getMessage());
	}

	private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		CORSRequestType type = CORSRequestType.detect(request);
		try {
			if (type.equals(CORSRequestType.ACTUAL)) {
				this.handleActualRequest(request, response);
				chain.doFilter(request, response);
			} else if (type.equals(CORSRequestType.PREFLIGHT)) {
				this.handlePreflightRequest(request, response);
			} else {
				printMessage(CORSException.GENERIC_HTTP_NOT_ALLOWED, response);
			}
		} catch (CORSException e) {
			printMessage(e, response);
		}
	}

	public void handleActualRequest(HttpServletRequest request, HttpServletResponse response) throws CORSException {
		if (CORSRequestType.detect(request) != CORSRequestType.ACTUAL) {
			throw CORSException.INVALID_ACTUAL_REQUEST;
		}
		// TODO
	}

	public void handlePreflightRequest(HttpServletRequest request, HttpServletResponse response) throws CORSException {
		if (CORSRequestType.detect(request) != CORSRequestType.PREFLIGHT) {
			throw CORSException.INVALID_PREFLIGHT_REQUEST;
		}
		// TODO
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (((request instanceof HttpServletRequest)) && ((response instanceof HttpServletResponse))) {
			doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
		} else {
			throw new ServletException("Cannot filter non-HTTP requests/responses");
		}
	}

	public void destroy() {
		// Do nothing
	}

}