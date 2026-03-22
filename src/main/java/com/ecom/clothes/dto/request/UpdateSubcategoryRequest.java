package com.ecom.clothes.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Size;

public record UpdateSubcategoryRequest(

		@JsonProperty("category_id") Long categoryId,

		@JsonProperty("name")
		@Size(min = 2, max = 100, message = "Subcategory name must be between 2 to 100 characters") String name,

		@JsonProperty("description")
		@Size(min = 2, max = 500, message = "Subcategory name must be between 2 to 500 characters") String description) {

}
