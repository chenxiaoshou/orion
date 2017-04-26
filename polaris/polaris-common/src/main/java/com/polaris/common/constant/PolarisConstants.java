package com.polaris.common.constant;

/**
 * Polaris系列项目相关的常量
 * 
 * @author John
 *
 */
public interface PolarisConstants {

	/**
	 * spring mvc dispatcher servlet name
	 */
	String DEFAULT_SERVLET_NAME = "Polaris";
	
	/**
	 * spring mvc dispatcher servlet mapping url pattern
	 */
	String POLARIS_MAPPING_URL_PATTERN = "/v1/*";
	
	/**
	 * druid stat view servlet name 查看sql统计
	 */
	String DRUID_SERVLET_NAME = "DruidStatView";
	
	/**
	 * druid stat view servlet mapping url pattern
	 */
	String DRUID_MAPPING_URL_PATTERN = "/druid/*";
	
	/**
	 * 登录druid view 统计页面的账号名
	 */
	String DRUID_STATVIEW_USERNAME = "druid";
	
	/**
	 * 登录druid view 统计页面的密码
	 */
	String DRUID_STATVIEW_PASSWORD = "1990912";
	
	/**
	 * druid stat view servlet allow view ip 允许访问stat view页面的ip地址
	 */
	String DRUID_STAT_ALLOW_IP = "192.168.199.103, 192.168.199.102, 192.168.199.101, 192.168.199.105, 192.168.199.106, 192.168.199.104";
	
	/**
	 * 配置principalSessionName，使得druid stat servlet能够知道当前的session的用户是谁
	 */
	String DRUID_STAT_PRINCIPAL_SESSION_NAME = "pms_user";
	
	/**
	 * UTF-8
	 */
	String CHAESET_UTF_8 = "UTF-8";

	/**
	 * GBK
	 */
	String CHAESET_GBK = "GBK";

	/**
	 * ISO8859-1
	 */
	String CHAESET_ISO8859_1 = "ISO8859-1";
	
	/**
	 *  jsp试图解析器前缀
	 */
	String VIEW_JSP_PREFIX = "/WEB-INF/jsp/";

	/**
	 *  jsp试图解析器后缀
	 */
	String VIEW_JSP_SUFFIX = ".jsp";

	/**
	 * text/html; charset=UTF-8
	 */
	String VIEW_RESOLVER_CONTENT_TYPE = "text/html; charset=UTF-8";
	
	/**
	 * 
	 */
	String CONTENT_TYPE = "text/html; charset=UTF-8";
	
	// 静态资源处理路径
	String RESOURCE_HANDLER = "/static/**";

	// 静态资源路径
	String RESOURCE_LOCATION = "/static/";

	// freemark视图后缀
	String VIEW_FREEMARKER_SUFFIX = ".ftl";

	String VIEW_FREEMARKER_TEMPLATE_LOADER_PATH = "/WEB-INF/freemarker/";

	// 消息国际化默认使用的模板名称
	String MESSAGE_SOURCE = "message_zh_CN";

	// polaris jpa persistence_unit_name
	String PERSISTENCE_UNIT_NAME = "Polaris";

}
