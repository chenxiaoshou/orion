package com.polaris.common.exception;

public class ApiException extends RuntimeException {

	private static final long serialVersionUID = -2229965410258306619L;

	private final String errorKey;

	private final String[] args;

	public ApiException(final String errorKey, final String... args) {
		this.errorKey = errorKey;
		this.args = args;
	}

	public ApiException(final String errorKey) {
		this(errorKey, new String[0]);
	}

	public String getErrorKey() {
		return errorKey;
	}

	public Object[] getArgs() {
		return args;
	}

}
