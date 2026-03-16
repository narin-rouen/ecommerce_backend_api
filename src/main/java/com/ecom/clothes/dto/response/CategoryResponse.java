package com.ecom.clothes.dto.response;

import java.time.LocalDateTime;

import com.ecom.clothes.entity.Category;

public record CategoryResponse(Long id, String name, String description, LocalDateTime createdAt,
		LocalDateTime updatedAt, LocalDateTime deletedAt) {

	public static CategoryResponse from(Category category) {
		return new CategoryResponse(category.getId(), category.getName(), category.getDescription(),
				category.getCreatedAt(), category.getUpdatedAt(), category.getDeletedAt());
	}
}
