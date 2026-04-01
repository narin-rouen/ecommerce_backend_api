package com.ecom.clothes.dto.response;

import java.util.List;

import org.springframework.data.domain.Sort;

import jakarta.validation.constraints.Min;

public record AddressPageResponse(List<AddressResponse> address,
		@Min(value = 0, message = "Page number must be non-negative") int page,

		@Min(value = 1, message = "Page size must be at least 1") int size,

		String sortBy, Sort.Direction direction, String search) {

}
