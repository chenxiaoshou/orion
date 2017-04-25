package com.polaris.common.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {

	private static final long serialVersionUID = -2229965410258306619L;

	private HttpStatus httpStatus;
	
	private ExceptionMessage exceptionMessage;
	
	public ApiException(){
	}
	
	public ApiException(HttpStatus httpStatus, ExceptionMessage exceptionMessage) {
		this.httpStatus = httpStatus;
		this.exceptionMessage = exceptionMessage;
	}
	
	public ExceptionMessage getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(ExceptionMessage exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

}
