package com.ecom.clothes.dto.response;

import java.util.List;

import com.ecom.clothes.entity.CartStatus;

public record CartResponse(Long id, UserSummaryResponse user, List<CartItemResponse> items, CartStatus status) {
	public static CartResponse from(com.ecom.clothes.entity.Cart cart) {
		return new CartResponse(cart.getId(), UserSummaryResponse.from(cart.getUser()),
				cart.getItems().stream().map(CartItemResponse::from).toList(), cart.getStatus());
	}
}
