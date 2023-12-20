package com.mitra.middleware.router.exception;

public class RuleManagerException extends RouterMediatorException {

	public RuleManagerException(String message, String errorCode) {
		super(message, errorCode);
	}

	public RuleManagerException(String message, String errorCode, Throwable e) {
		super(message, errorCode, e);
	}
}