package com.ecom.clothes.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record CategoryDetailsResponse(Long id, String name, String description, LocalDateTime createdAt,
		LocalDateTime updatedAt, LocalDateTime deletedAt, List<SubcategoryResponse> subcategory) {

}
