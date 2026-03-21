package com.ecom.clothes.dto.response;

import java.time.LocalDateTime;

import com.ecom.clothes.entity.Subcategory;

public record SubcategoryResponse(

		Long id, Long categoryId, String name, String description, LocalDateTime createdAt, LocalDateTime updatedAt,
		LocalDateTime deletedAt) {

	public static SubcategoryResponse from(Subcategory subcategory) {
		return new SubcategoryResponse(subcategory.getId(), subcategory.getCategory().getId(), subcategory.getName(),
				subcategory.getDescription(), subcategory.getCreatedAt(), subcategory.getUpdatedAt(),
				subcategory.getDeletedAt());
	}

}
