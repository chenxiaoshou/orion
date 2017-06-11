package com.orion.common.cors;

/**
 * CORS filter configuration exception, intended to report invalid init
 * parameters at startup.
 *
 * @author Vladimir Dzhuvinov
 */
public class CORSConfigurationException extends Exception {

	private static final long serialVersionUID = 2762388955820600682L;

	/**
	 * Creates a new CORS filter configuration exception with the specified
	 * message.
	 * 
	 * @param message
	 *            The exception message.
	 */
	public CORSConfigurationException(final String message) {

		super(message);
	}

	/**
	 * Creates a new CORS filter configuration exception with the specified
	 * message and cause.
	 *
	 * @param message
	 *            The exception message.
	 * @param cause
	 *            The exception cause.
	 */
	public CORSConfigurationException(final String message, final Throwable cause) {

		super(message, cause);
	}
}
