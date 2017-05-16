package com.polaris.manage.web.advice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.polaris.common.exception.ApiException;
import com.polaris.common.exception.ExceptionMessage;

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

	// TODO 暂时还没有想好要怎么设计，先要看看BindingResult中的数据到底是什么样子之后才能决定怎么写。。。
	@ExceptionHandler(BindException.class)
	public ResponseEntity<ExceptionMessage> handleBindException(BindException e) {
		BindingResult br = e.getBindingResult();
		LOGGER.debug(br);
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	// 处理更具体的异常
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ExceptionMessage> handleApiException(ApiException e) {
		return new ResponseEntity<>(e.getExceptionMessage(), e.getHttpStatus());
	}

	// 处理通用异常
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
