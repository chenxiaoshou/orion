package com.polaris.manage.web.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.polaris.common.exception.ApiException;
import com.polaris.common.exception.ErrorCode;
import com.polaris.common.exception.ExceptionType;
import com.polaris.common.utils.JsonUtil;

/**
 * 专门用来处理带有RestController和Controller注解的controller层面的异常
 * 
 * @author John
 *
 */
@RestControllerAdvice(annotations = { RestController.class, Controller.class })
public class ExceptionHandleAdvice {

	// 处理通用异常
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e) {
		return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	// 处理更具体的异常
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiException> handleApiException(ApiException e) {
		return new ResponseEntity<ApiException>(e, e.getHttpStatus());
	}
	
	public static void main(String[] args) {
		ApiException e = new ApiException(HttpStatus.BAD_REQUEST, ExceptionType.ERROR,
				ErrorCode.CODE_10001.getErrCode(), "balabalabala", "",
				new IllegalArgumentException("dawdawdaw").getCause());
		try {
			String json = JsonUtil.toJSON(e);
			System.out.println(json);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
}
