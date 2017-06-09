package com.polaris.manage.web.controller;

/**
 * 提供所有Controller中的通用方法
 * 
 * @author John
 *
 */
public class BaseController {

	private static final String IP_UNKNOWN = "Unknown";

	private static final String IP_ZERO = "0:0:0:0:0:0:0:1";

	private static final String IP_LOCALHOST = "127.0.0.1";

	protected String getRemoteHost(javax.servlet.http.HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		// 多级反向代理之后，x-forwarded-for请求头中的ip会变成多个ip拼接串(128.244.32.123,196.128.199.106),取第一个不是unknow的Ip地址，即为真实的客户端ip
		if (ip != null && ip.length() > 15) {
			String[] ips = ip.split(",");
			for (String subIp : ips) {
				if (!IP_UNKNOWN.equalsIgnoreCase(subIp)) {
					ip = subIp;
					break;
				}
			}
		}
		return IP_ZERO.equals(ip) ? IP_LOCALHOST : ip;
	}

}
