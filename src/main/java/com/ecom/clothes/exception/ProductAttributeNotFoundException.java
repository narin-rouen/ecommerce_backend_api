package com.ecom.clothes.exception;

public class ProductAttributeNotFoundException extends RuntimeException {
	public ProductAttributeNotFoundException(String message) {
		super(message);
	}

	public ProductAttributeNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
