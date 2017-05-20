package com.polaris.common.exception;

/**
 * 封装应用范围内的所有检查、非检查异常，并抛到视图层交由异常处理切面统一处理
 * @author John
 *
 */
public class PolarisException extends RuntimeException {

	private static final long serialVersionUID = 3537342038034363732L;

	public PolarisException() {
		super();
	}
	
	public PolarisException(String errMsg, Throwable cause) {
		super(errMsg, cause);
	}

	public PolarisException(String errMsg) {
		super(errMsg);
	}

}
