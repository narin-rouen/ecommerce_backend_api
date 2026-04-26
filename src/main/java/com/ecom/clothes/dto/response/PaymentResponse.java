package com.ecom.clothes.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ecom.clothes.entity.Payment;

public record PaymentResponse(Long id, BigDecimal amount, String provider, String status, LocalDateTime createdAt,
		LocalDateTime updatedAt) {

	public static PaymentResponse fromEntity(Payment payment) {
		return new PaymentResponse(payment.getId(), payment.getAmount(), payment.getProvider(),
				payment.getStatus().name(), payment.getCreatedAt(), payment.getUpdatedAt());
	}

}
