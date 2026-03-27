package com.ecom.clothes.dto.response;

import java.time.LocalDateTime;

import com.ecom.clothes.entity.Product;

public record ProductResponse(Long id, String name, String description, String imageFileName, LocalDateTime createdAt,
		LocalDateTime updatedAt, LocalDateTime deletedAt, SubcategoryInfo subcategory) {
	public static ProductResponse from(Product product) {
		SubcategoryInfo subcategoryInfo = null;

		if (product.getSubcategory() != null) {
			subcategoryInfo = new SubcategoryInfo(product.getSubcategory().getId(), product.getSubcategory().getName());
		}

		return new ProductResponse(product.getId(), product.getName(), product.getDescription(),
				product.getImageFileName(), product.getCreatedAt(), product.getUpdatedAt(), product.getDeletedAt(),
				subcategoryInfo);
	}

	public record SubcategoryInfo(Long subcategoryId, String subcategoryName) {
	}
}
