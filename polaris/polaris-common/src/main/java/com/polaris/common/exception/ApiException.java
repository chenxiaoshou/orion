package com.polaris.common.exception;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ApiException extends RuntimeException {

	private static final long serialVersionUID = 4401932271514516174L;

	private final HttpStatus httpStatus; // Http状态码

	private final ExceptionType type; // 异常类型(warnning, error)

	private final int code; // 应用内部自定义错误代码

	private final String message; // 错误信息

	private final String moreInfoUrl; // 错误详情页，该页面会以用户友好的页面显示完整的错误异常信息

	@JsonIgnore
	private final Throwable throwable;

	public ApiException(HttpStatus httpStatus, ExceptionType type, int code, String message, String moreInfoUrl,
			Throwable throwable) {
		if (httpStatus == null) {
			throw new NullPointerException("httpStatus must not be null");
		}
		this.httpStatus = httpStatus;
		this.code = code;
		this.type = type;
		this.message = message;
		this.moreInfoUrl = moreInfoUrl;
		this.throwable = throwable;
	}

	public int getCode() {
		return code;
	}

	
	public String getMessage() {
		return message;
	}

	public String getMoreInfoUrl() {
		return moreInfoUrl;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public ExceptionType getType() {
		return type;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

}
