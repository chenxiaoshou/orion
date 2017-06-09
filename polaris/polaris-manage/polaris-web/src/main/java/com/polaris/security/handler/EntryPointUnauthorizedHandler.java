package com.polaris.security.handler;

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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.polaris.common.constant.ExceptionConstants;
import com.polaris.common.constant.PolarisConstants;
import com.polaris.common.exception.AppMessage;
import com.polaris.common.utils.JsonUtil;

/**
 * 这个入口点其实仅仅是被ExceptionTranslationFilter引用
 * 由此入口决定redirect、forward的操作
 * 
 * @author John
 *
 */
@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {

	private static final Logger LOGGER = LogManager.getLogger(EntryPointUnauthorizedHandler.class);

	@Autowired
	private MessageSource messageSource;

	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			AuthenticationException e) throws IOException, ServletException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.warn("Auth failure [" + e.getMessage() + "]");
		}
		String message = messageSource.getMessage(ExceptionConstants.UNAUTHORIZED_EXCEPTION, null, null);
		AppMessage appMessage = JsonUtil.fromJSON(message, AppMessage.class);
		appMessage.setMoreInfo(e.getMessage());
		httpServletResponse.addHeader("Content-type", MediaType.APPLICATION_JSON_UTF8_VALUE);
		httpServletResponse.setCharacterEncoding(PolarisConstants.CHAESET_UTF_8);
		httpServletResponse.setStatus(appMessage.getHttpStatus());
		PrintWriter printWriter = httpServletResponse.getWriter();
		printWriter.write(JsonUtil.toJSON(appMessage));
		printWriter.flush();
		printWriter.close();
	}

}
