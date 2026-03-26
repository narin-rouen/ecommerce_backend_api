package com.ecom.clothes.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.ecom.clothes.entity.Category;

public record CategoryDetailsResponse(Long id, String name, String description, LocalDateTime createdAt,
		LocalDateTime updatedAt, LocalDateTime deletedAt, List<SubcategoryResponse> subcategory) {

	public static CategoryDetailsResponse from(Category category, List<SubcategoryResponse> subcategoryResponses) {
		return new CategoryDetailsResponse(category.getId(), category.getName(), category.getDescription(),
				category.getCreatedAt(), category.getUpdatedAt(), category.getDeletedAt(), subcategoryResponses);
	}
}
