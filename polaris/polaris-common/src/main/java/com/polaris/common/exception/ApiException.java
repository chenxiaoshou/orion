package com.polaris.common.exception;

import com.polaris.common.exception.entity.ExceptionMessage;

public class ApiException extends RuntimeException {

	private static final long serialVersionUID = 1125194802465400071L;
	
	private ExceptionMessage exceptionMessage;

	public ExceptionMessage getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(ExceptionMessage exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

}
