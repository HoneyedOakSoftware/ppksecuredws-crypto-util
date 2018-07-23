package com.honeyedoak.cryptoutils.exception;

public class CryptoException extends RuntimeException {

	public CryptoException() {
	}

	public CryptoException(Exception ex) {
		this(ex.getMessage(), ex.getCause());
	}

	public CryptoException(String message) {
		super(message);
	}

	public CryptoException(String message, Throwable cause) {
		super(message, cause);
	}

	public CryptoException(Throwable cause) {
		super(cause);
	}

	public CryptoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
