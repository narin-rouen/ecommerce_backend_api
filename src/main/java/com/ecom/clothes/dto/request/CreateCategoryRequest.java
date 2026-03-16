package com.ecom.clothes.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCategoryRequest(

		@NotBlank(message = "Category name is required")
		@Size(min = 2, max = 100, message = "Category name must be between 2 to 100 characters")
		@JsonProperty("name") String name,

		@NotBlank(message = "Category description is required")
		@Size(min = 2, max = 500, message = "Category description must be between 2 to 500 characters")
		@JsonProperty("description") String description

) {

}
