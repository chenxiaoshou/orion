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
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.polaris.common.constant.ExceptionConstants;
import com.polaris.common.exception.AppMessage;
import com.polaris.common.utils.JsonUtil;

@Component
public class RestAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private static final Logger LOG = LogManager.getLogger(RestAuthenticationFailureHandler.class);

	@Autowired
	private MessageSource messageSource;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		LOG.warn("Auth failure [" + exception.getMessage() + "]");
		String message = messageSource.getMessage(ExceptionConstants.UNAUTHORIZED_EXCEPTION, null, null);
		AppMessage appMessage = JsonUtil.fromJSON(message, AppMessage.class);
		appMessage.setMoreInfo(exception.getMessage());
		response.setStatus(appMessage.getHttpStatus());
		response.addHeader("Content-type", MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.setCharacterEncoding("UTF-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(JsonUtil.toJSON(appMessage));
		printWriter.flush();
		printWriter.close();
	}
}
