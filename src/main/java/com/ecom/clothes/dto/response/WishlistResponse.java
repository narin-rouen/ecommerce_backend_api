package com.ecom.clothes.dto.response;

import java.time.LocalDateTime;

import com.ecom.clothes.entity.Wishlist;

public record WishlistResponse(Long id, UserSummaryResponse user, ProductSkuResponse productSku,
		LocalDateTime createdAt, LocalDateTime updatedAt) {

	public static WishlistResponse fromEntity(Wishlist wishlist) {
		return new WishlistResponse(wishlist.getId(), UserSummaryResponse.from(wishlist.getUser()),
				ProductSkuResponse.from(wishlist.getProductSku()), wishlist.getCreatedAt(), wishlist.getUpdatedAt());
	}
}
