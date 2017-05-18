package com.polaris.manage.web.advice;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.polaris.common.constant.ExceptionConstants;
import com.polaris.common.exception.ApiException;
import com.polaris.common.exception.ExceptionMessage;
import com.polaris.common.exception.PolarisException;
import com.polaris.common.utils.JsonUtil;

/**
 * 专门用来处理带有RestController和Controller注解的controller层面的异常.
 * 异常匹配顺序是从上到下，匹配到合适的异常处理程序之后就不再向下匹配
 * 
 * @author John
 *
 */
@RestControllerAdvice(annotations = { RestController.class, Controller.class })
public class ExceptionHandleAdvice {

	private static final Logger LOGGER = LogManager.getLogger(ExceptionHandleAdvice.class);
	
	@Autowired
	private MessageSource messageSource;

	// TODO 暂时还没有想好要怎么设计，先要看看BindingResult中的数据到底是什么样子之后才能决定怎么写。。。
	@ExceptionHandler(BindException.class)
	public ResponseEntity<ExceptionMessage> handleBindException(BindException e) {
		BindingResult br = e.getBindingResult();
		LOGGER.debug(br);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	// 处理视图层抛出的API异常
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ExceptionMessage> handleApiException(ApiException e) {
		String message = messageSource.getMessage(e.getErrorKey(), e.getArgs(), null);
		if (StringUtils.isBlank(message)) {
			message = messageSource.getMessage(ExceptionConstants.UNKNOWN_EXCEPTION, null, null);
		}
		ExceptionMessage exceptionMessage = JsonUtil.fromJSON(message, ExceptionMessage.class);
		return new ResponseEntity<>(exceptionMessage, HttpStatus.valueOf(exceptionMessage.getHttpStatus()));
	}

	// 处理底层抛出的经过封装的应用内异常
	@ExceptionHandler(PolarisException.class)
	public ResponseEntity<ExceptionMessage> handlePolarisException(PolarisException e) {
		String message = messageSource.getMessage(ExceptionConstants.APPLICATION_EXCEPTION, null, null);
		ExceptionMessage exceptionMessage = JsonUtil.fromJSON(message, ExceptionMessage.class);
		return new ResponseEntity<>(exceptionMessage, HttpStatus.valueOf(exceptionMessage.getHttpStatus()));
	}
	
	// 处理通用异常
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionMessage> handleException(Exception e) {
		String message = messageSource.getMessage(ExceptionConstants.UNKNOWN_EXCEPTION, null, null);
		ExceptionMessage exceptionMessage = JsonUtil.fromJSON(message, ExceptionMessage.class);
		return new ResponseEntity<>(exceptionMessage, HttpStatus.valueOf(exceptionMessage.getHttpStatus()));
	}

}
