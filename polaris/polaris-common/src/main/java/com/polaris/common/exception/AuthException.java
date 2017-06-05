package com.polaris.common.exception;

/**
 * 权限验证异常
 * @author John
 *
 */
public class AuthException extends ApiException {

	private static final long serialVersionUID = 3262496088033532197L;
	
	public AuthException(String errorKey) {
		super(errorKey);
	}
	
}
