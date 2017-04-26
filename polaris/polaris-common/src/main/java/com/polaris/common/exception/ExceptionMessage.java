package com.polaris.common.exception;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ExceptionMessage implements Serializable {

	private static final long serialVersionUID = 8936744394607039058L;

	private final int httpStatus; // Http状态码

	private final String type; // 异常类型(warnning, error)

	private final int code; // 应用内部自定义错误代码

	private final String message; // 错误信息

	private final String moreInfoUrl; // 错误详情页，该页面会以用户友好的页面显示完整的错误异常信息

	@JsonIgnore
	private final Throwable throwable;

	public ExceptionMessage(int httpStatus, String type, int code, String message, String moreInfoUrl,
			Throwable throwable) {
		if (httpStatus == 0) {
			httpStatus = HttpStatus.BAD_REQUEST.value();
		}
		if (StringUtils.isBlank(type)) {
			type = ExceptionType.ERROR.getDesc();
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

	public String getType() {
		return type;
	}

	public int getHttpStatus() {
		return httpStatus;
	}
	

}
