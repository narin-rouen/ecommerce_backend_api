package com.ecom.clothes.dto.response;

import java.time.LocalDateTime;

import com.ecom.clothes.entity.ProductAttribute;
import com.ecom.clothes.entity.ProductAttributeType;

public record ProductAttributeResponse(Long id, ProductAttributeType type, String value, LocalDateTime createdAt,
		LocalDateTime updatedAt, LocalDateTime deletedAt) {

	public static ProductAttributeResponse from(ProductAttribute entity) {
		return new ProductAttributeResponse(entity.getId(), entity.getType(), entity.getValue(), entity.getCreatedAt(),
				entity.getUpdatedAt(), entity.getDeletedAt());
	}
}
