package com.ecom.clothes.dto.response;

import java.math.BigDecimal;

import com.ecom.clothes.entity.CartItem;

public record CartItemResponse(Long id, Long cartId, ProductSkuResponse productSku, Integer quantity,
		BigDecimal unitPrice) {

	public static CartItemResponse from(CartItem cartItem) {
		return new CartItemResponse(cartItem.getId(), cartItem.getCart().getId(),
				ProductSkuResponse.from(cartItem.getProductSku()), cartItem.getQuantity(), cartItem.getUnitPrice());
	}
}
