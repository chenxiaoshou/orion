package com.polaris.common.filter.dic;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpMethod;

/**
 * 请求类型
 * 
 * @author John
 *
 */
public enum CORSRequestType {

	ACTUAL,

	PREFLIGHT,

	OTHER;

	public static CORSRequestType detect(HttpServletRequest request) {
		if (request.getHeader("Origin") == null) {
			return OTHER;
		}
		String serverOrigin = request.getScheme() + "://" + request.getHeader("Host");
		if ((request.getHeader("Host") != null) && (request.getHeader("Origin").equals(serverOrigin))) {
			return OTHER;
		}
		if ((request.getHeader("Access-Control-Request-Method") != null) && (request.getMethod() != null)
				&& (request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name()))) {
			return PREFLIGHT;
		}
		return ACTUAL;
	}

}