package com.polaris.common.exception;

public class BeanCopyException extends Exception {

	private static final long serialVersionUID = 1368968087186796425L;

	public BeanCopyException(final String errorMsg, final Throwable cause) {
		super(errorMsg, cause);
	}

}
