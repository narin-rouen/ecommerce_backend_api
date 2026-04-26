package com.ecom.clothes.dto.response;

import com.ecom.clothes.entity.OrderItem;

public record OrderItemResponse(Long id, ProductSkuResponse productSku, Integer quantity) {

	public static OrderItemResponse fromEntity(OrderItem orderItem) {
		ProductSkuResponse productSkuResponse = ProductSkuResponse.from(orderItem.getProductSku());
		return new OrderItemResponse(orderItem.getId(), productSkuResponse, orderItem.getQuantity().intValue());
	}

}
