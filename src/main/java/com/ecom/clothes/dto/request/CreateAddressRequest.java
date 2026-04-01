package com.ecom.clothes.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateAddressRequest(
		@NotBlank(message = "Title is required")
		@Size(min = 2, max = 100, message = "Title must be between 2 to 100 characters") String title,

		@NotBlank(message = "Line 1 is required")
		@Size(min = 2, max = 100, message = "Line 1 must be between 2 to 100 characters") String line1,

		@Size(min = 2, max = 100, message = "Line 1 must be between 2 to 100 characters") String line2,

		@NotBlank(message = "Country is required")
		@Size(min = 2, max = 100, message = "Country must be between 2 to 100 characters") String country,

		@NotBlank(message = "City is required")
		@Size(min = 2, max = 100, message = "City must be between 2 to 100 characters") String city,

		@NotBlank(message = "Postcode is required")
		@Size(min = 2, max = 15, message = "Postcode must be between 2 to 15 characters") String postcode) {

}
