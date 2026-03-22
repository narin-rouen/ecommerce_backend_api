package com.ecom.clothes.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateSubcategoryRequest(

		@JsonProperty("category_id") @NotNull(message = "Category id is required") Long categoryId,

		@JsonProperty("name")
		@NotBlank(message = "Subcategory name is required")
		@Size(min = 2, max = 100, message = "Subcategory name must be between 2 to 100 characters") String name,

		@JsonProperty("description")
		@NotBlank(message = "Subcategory description is required")
		@Size(min = 2, max = 500, message = "Subcategory name must be between 2 to 500 characters") String description) {

}
