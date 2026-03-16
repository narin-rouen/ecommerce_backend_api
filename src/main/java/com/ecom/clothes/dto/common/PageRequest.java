package com.ecom.clothes.dto.common;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import jakarta.validation.constraints.Min;

public record PageRequest(@Min(value = 0, message = "Page number must be non-negative") Integer page,

		@Min(value = 1, message = "Page size must be at least 1") Integer size,

		String sortBy, Sort.Direction direction, String search) {

	public Pageable toPageable() {
		int pageNum = page != null ? page : 0;
		int pageSize = size != null ? size : 10;

		if (sortBy != null && direction != null) {
			return org.springframework.data.domain.PageRequest.of(pageNum, pageSize, Sort.by(direction, sortBy));
		}
		return org.springframework.data.domain.PageRequest.of(pageNum, pageSize);
	}

	public PageRequest {
		if (page == null || page < 0) {
			page = 0;
		}
		if (size == null || size <= 0) {
			size = 10;
		}
	}
}
