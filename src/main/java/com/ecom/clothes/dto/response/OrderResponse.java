package com.ecom.clothes.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponse(Long id, UserSummaryResponse user, PaymentResponse payment, BigDecimal total, String status,
		OrderItemResponse[] items, LocalDateTime createdAt, LocalDateTime updatedAt) {

	public static OrderResponse fromEntity(com.ecom.clothes.entity.Order order) {
		UserSummaryResponse userResponse = UserSummaryResponse.from(order.getUser());
		PaymentResponse paymentResponse = PaymentResponse.fromEntity(order.getPayment());
		OrderItemResponse[] itemResponses = order.getItems().stream().map(OrderItemResponse::fromEntity)
				.toArray(OrderItemResponse[]::new);

		return new OrderResponse(order.getId(), userResponse, paymentResponse, order.getTotal(),
				order.getStatus().name(), itemResponses, order.getCreatedAt(), order.getUpdatedAt());
	}

}
