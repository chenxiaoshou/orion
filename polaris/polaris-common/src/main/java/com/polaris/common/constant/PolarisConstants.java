package com.polaris.common.constant;

/**
 * Polaris系列项目相关的常量
 * 
 * @author John
 *
 */
public interface PolarisConstants {

	String DEFAULT_SERVLET_NAME = "Polaris";
	
	String CHAESET_UTF_8 = "UTF-8";

	String CHAESET_GBK = "UTF-8";

	// jsp试图解析器前缀
	String VIEW_JSP_PREFIX = "/WEB-INF/jsp/";

	// jsp试图解析器后缀
	String VIEW_JSP_SUFFIX = ".jsp";

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

}
