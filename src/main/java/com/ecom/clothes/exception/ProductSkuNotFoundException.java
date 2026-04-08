package com.ecom.clothes.exception;

public class ProductSkuNotFoundException extends RuntimeException {
	public ProductSkuNotFoundException(String message) {
		super(message);
	}

	public ProductSkuNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
