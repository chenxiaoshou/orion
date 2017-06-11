package com.polaris.common.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.polaris.common.constant.ExceptionConstants;
import com.polaris.common.exception.AppMessage;
import com.polaris.common.utils.JsonUtil;

/**
 * 拒绝访问
 * 
 * @author XueLiang
 * @date 2017年3月1日 上午11:07:10
 * @version 1.0
 */
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

	private static final Logger LOGGER = LogManager.getLogger(RestAccessDeniedHandler.class);

	@Autowired
	private MessageSource messageSource;
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		LOGGER.warn("Permission denied [" + accessDeniedException.getMessage() + "]");
		String message = messageSource.getMessage(ExceptionConstants.FORBIDDEN_EXCEPTION, null, null);
		AppMessage appMessage = JsonUtil.fromJSON(message, AppMessage.class);
		appMessage.setMoreInfo(accessDeniedException.getMessage());
		response.setStatus(appMessage.getHttpStatus());
		response.addHeader("Content-type", MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.setCharacterEncoding("UTF-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(JsonUtil.toJSON(appMessage));
		printWriter.flush();
		printWriter.close();
	}
}
