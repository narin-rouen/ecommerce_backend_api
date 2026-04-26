package com.ecom.clothes.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreatePaymentRequest(
		@NotNull(message = "Amount is required")
		@DecimalMin(value = "0.01", message = "Amount must be greater than 0") BigDecimal amount,

		@NotBlank(message = "Payment provider is required") String provider,

		@NotBlank(message = "Payment method is required") String paymentMethod,

		String cardNumber, String expiryDate, String cvv) {

}
