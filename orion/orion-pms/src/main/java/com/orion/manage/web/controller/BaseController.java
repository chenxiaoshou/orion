package com.orion.manage.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.orion.common.constant.SecurityConstants;
import com.orion.common.dic.SourceTypeEnum;
import com.orion.common.exception.ApiException;

/**
 * 提供所有Controller中的通用方法
 * 
 * @author John
 *
 */
public class BaseController {

	private static final Logger LOGGER = LogManager.getLogger(BaseController.class);

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

	/**
	 * 新增操作之后，在response的header中添加新增资源的url地址。
	 * 
	 * @param response
	 * @param id
	 */
	public void buildRedirectUrl(HttpServletRequest request, HttpServletResponse response, String id) {
		String requestUrl = request.getRequestURL().toString();
		String location = "";
		if (requestUrl.endsWith("/")) {
			location = requestUrl + id;
		} else {
			location = requestUrl + "/" + id;
		}
		LOGGER.trace("location url [" + location + "]");
		response.addHeader("Location", location);
	}

	/**
	 * 从Source请求头中提取请求方的标识（Desktop, Android, IOS, H5...）
	 * 
	 * @param request
	 * @return
	 */
	public SourceTypeEnum getSourceType(HttpServletRequest request) {
		SourceTypeEnum sourceTypeEnum = null;
		String sourceStr = request.getHeader(SecurityConstants.HEADER_SOURCE);
		if (StringUtils.isNotBlank(sourceStr)) {
			sourceTypeEnum = SourceTypeEnum.getSourceTypeByCode(Integer.valueOf(sourceStr));
		}
		if (sourceTypeEnum == null) {
			throw new ApiException("global.source.is_null");
		}
		return sourceTypeEnum;
	}

}
