package com.ecom.clothes.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Size;

public record UpdateProductRequest(

		@JsonProperty("name")
		@Size(min = 2, max = 100, message = "Product name must be between 2 to 100 characters") String name,

		@JsonProperty("description")
		@Size(min = 15, max = 500, message = "Product description must between 15 to 500 charaters") String description,

		@JsonProperty("image_file_name") String imageFileName,

		@JsonProperty("subcategory_id") Long subcategoryId) {

}
