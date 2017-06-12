package com.orion.common.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.orion.common.constant.ExceptionConstants;
import com.orion.common.srv.AppMessageService;
import com.orion.common.utils.JsonUtil;

/**
 * 此处的全局异常处理和ExceptionHandleAdvice异常处理互补，那里没有捕获到的一些次要的异常，在这里封装之后捕获下来
 * 
 * @author John
 *
 */
@Component
public class AppExceptionResolver implements HandlerExceptionResolver, Ordered {

	private static final Logger LOG = LogManager.getLogger(AppExceptionResolver.class);

	@Autowired
	private AppMessageService appMessageSource;

	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		AppMessage appMessage = generateAppMessage(request, response, ex);
		if (appMessage == null) {
			return null;
		}
		try {
			response.addHeader("Content-type", MediaType.APPLICATION_JSON_UTF8_VALUE);
			response.setCharacterEncoding("UTF-8");
			PrintWriter printWriter;
			printWriter = response.getWriter();
			printWriter.write(JsonUtil.toJSON(appMessage));
			printWriter.flush();
			printWriter.close();
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		return new ModelAndView();
	}

	// 这里只处理有限的几个异常，其他异常交由ExceptionHandleAdvice处理
	@SuppressWarnings("deprecation")
	private AppMessage generateAppMessage(HttpServletRequest request, HttpServletResponse response, Exception ex) {
		Integer code = null;
		if (ex instanceof org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException
				|| ex instanceof NoHandlerFoundException) {
			code = HttpServletResponse.SC_NOT_FOUND;
		} else if (ex instanceof HttpMediaTypeNotAcceptableException) {
			code = HttpServletResponse.SC_NOT_ACCEPTABLE;
		} else if (ex instanceof MissingPathVariableException) {
			code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		} else if (ex instanceof MissingServletRequestParameterException || ex instanceof ServletRequestBindingException
				|| ex instanceof TypeMismatchException || ex instanceof MissingServletRequestPartException) {
			code = HttpServletResponse.SC_BAD_REQUEST;
		} else if (ex instanceof AsyncRequestTimeoutException && !response.isCommitted()) {
			code = HttpServletResponse.SC_SERVICE_UNAVAILABLE;
		} else if (ex instanceof AuthenticationException) {
			code = HttpServletResponse.SC_UNAUTHORIZED;
		}
		AppMessage appMessage = null;
		if (code != null) {
			appMessage = this.appMessageSource.getAppMessage(ExceptionConstants.RESOLVE_EXCEPTION,
					new Object[] { code, ex.getMessage() });
			appMessage.setMoreInfo(ex.getMessage());
		}
		return appMessage;
	}

}
