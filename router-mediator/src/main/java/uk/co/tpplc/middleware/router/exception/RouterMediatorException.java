package com.mitra.middleware.router.exception;

public class RouterMediatorException extends Exception {

	private final String errorCode;

	public RouterMediatorException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public RouterMediatorException(String message, String errorCode, Throwable e) {
		super(message, e);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}
}