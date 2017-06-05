package com.polaris.common.constant;

/**
 * Polaris系列项目相关的常量
 * 
 * @author John
 *
 */
public final class PolarisConstants {

	private PolarisConstants() {

	}

	/**
	 * spring mvc dispatcher servlet name
	 */
	public static final String DEFAULT_SERVLET_NAME = "Polaris";

	/**
	 * API请求路径前缀
	 */
	public static final String POLARIS_API_MAPPING_URL_PATTERN = "/api/v1/*";
	
	/**
	 * 管理系统请求路径前缀
	 */
	public static final String POLARIS_ADMIN_MAPPING_URL_PATTERN = "/admin/v1/*";

	/**
	 * druid stat view servlet name 查看sql统计
	 */
	public static final String DRUID_SERVLET_NAME = "DruidStatView";

	/**
	 * druid stat view servlet mapping url pattern
	 */
	public static final String DRUID_MAPPING_URL_PATTERN = "/druid/*";

	/**
	 * 登录druid view 统计页面的账号名
	 */
	public static final String DRUID_STATVIEW_USERNAME = "druid";

	/**
	 * 登录druid view 统计页面的密码
	 */
	public static final String DRUID_STATVIEW_PASSWORD = "1990912";

	/**
	 * druid stat view servlet allow view ip 允许访问stat view页面的ip地址
	 */
	public static final String DRUID_STAT_ALLOW_IP = "192.168.199.103, 192.168.199.102, 192.168.199.101, 192.168.199.105, 192.168.199.106, 192.168.199.104";

	/**
	 * 配置principalSessionName，使得druid stat servlet能够知道当前的session的用户是谁
	 */
	public static final String DRUID_STAT_PRINCIPAL_SESSION_NAME = "pms_user";

	/**
	 * UTF-8
	 */
	public static final String CHAESET_UTF_8 = "UTF-8";

	/**
	 * GBK
	 */
	public static final String CHAESET_GBK = "GBK";

	/**
	 * ISO8859-1
	 */
	public static final String CHAESET_ISO8859_1 = "ISO8859-1";

	/**
	 * jsp试图解析器前缀
	 */
	public static final String VIEW_JSP_PREFIX = "/WEB-INF/jsp/";

	/**
	 * jsp试图解析器后缀
	 */
	public static final String VIEW_JSP_SUFFIX = ".jsp";

	/**
	 * text/html; charset=UTF-8
	 */
	public static final String CONTENT_TYPE = "text/html; charset=UTF-8";

	/**
	 * 静态资源处理路径
	 */
	public static final String RESOURCE_HANDLER = "/static/**";

	/**
	 * 静态资源路径
	 */
	public static final String RESOURCE_LOCATION = "/static/";

	/**
	 * Freemark视图后缀
	 */
	public static final String VIEW_FREEMARKER_SUFFIX = ".ftl";

	/**
	 * Freemark视图前缀
	 */
	public static final String VIEW_FREEMARKER_TEMPLATE_LOADER_PATH = "/WEB-INF/freemarker/";

	/**
	 * 消息国际化默认使用的模板名称
	 */
	public static final String MESSAGE_SOURCE = "i18n.message,i18n.operate";

	/**
	 * polaris jpa persistence_unit_name
	 */
	public static final String PERSISTENCE_UNIT_NAME = "Polaris";

	/**
	 * 扫描国际化消息失败时，提供的默认消息提示 >> "未知异常"
	 */
	public static final String UNKNOWN_EXCEPTION = "global.unknown_exception";

	public static final String POLARIS_AUTH_DB = "polaris_auth";

	public static final String VALUE_ACCESS_CONTROL_ALLOW_CREDENTIALS = "true";

	public static final String VALUE_ACCESS_CONTROL_ALLOW_HEADERS = "Origin,X-Requested-With,Content-Type,Accept,Cache-Control,X-Auth-Token";

	public static final String VALUE_ACCESS_CONTROL_ALLOW_METHODS = "GET,PUT,POST,DELETE,OPTIONS,HEAD";

	public static final String VALUE_ACCESS_CONTROL_ALLOW_ORIGIN = "http://www.polaris.com,https://www.polaris.com";

	public static final String VALUE_ACCESS_CONTROL_EXPOSE_HEADERS = "Origin,X-Requested-With,Content-Type,Accept,Cache-Control";

	public static final String VALUE_ACCESS_CONTROL_MAX_AGE = String.valueOf(60 * 60 * 24); // 预请求有效期保留一天

}
