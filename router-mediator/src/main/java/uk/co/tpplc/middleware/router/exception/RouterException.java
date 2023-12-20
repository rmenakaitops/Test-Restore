package com.mitra.middleware.router.exception;

public class RouterException extends RouterMediatorException {

	public RouterException(String message, String errorCode) {
		super(message, errorCode);
	}

	public RouterException(String message, String errorCode, Throwable e) {
		super(message, errorCode, e);
	}
}