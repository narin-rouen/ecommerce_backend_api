package com.ecom.clothes.exception;

public class SubcategoryNotFoundException extends RuntimeException {
	public SubcategoryNotFoundException(String message) {
		super(message);
	}

	public SubcategoryNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
