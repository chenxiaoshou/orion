package com.polaris.common.exception;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ExceptionMessage implements Serializable {

	private static final long serialVersionUID = 8936744394607039058L;

	private int httpStatus; // Http状态码

	private String type; // 异常类型(WARNNING, ERROR)

	private int code; // 应用内部自定义错误代码

	private String message; // 错误信息

	@JsonIgnore
	private Throwable throwable;

	public ExceptionMessage(int httpStatus, String type, int code, String message, Throwable throwable) {
		this.code = code;
		this.httpStatus = httpStatus;
		this.type = type;
		this.message = message;
		this.throwable = throwable;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public String getType() {
		return StringUtils.isBlank(type) ? ExceptionType.ERROR.getDesc() : type;
	}

	public int getHttpStatus() {
		return httpStatus == 0 ? HttpStatus.BAD_REQUEST.value() : httpStatus;
	}

}
