package com.polaris.common.exception;

/**
 * 专门处理APIController视图层的异常
 * 
 * @author John
 *
 */
public class ApiException extends PolarisException {

	private static final long serialVersionUID = -2229965410258306619L;

	private final String errorKey;

	private final String[] args;

	public ApiException(String errorKey, String[] args) {
		this.errorKey = errorKey;
		this.args = args;
	}

	public ApiException(String errorKey) {
		this(errorKey, new String[0]);
	}

	public String getErrorKey() {
		return errorKey;
	}

	public Object[] getArgs() {
		return args;
	}

}