package com.ecom.clothes.dto.request;

import jakarta.validation.constraints.Size;

public record UpdateAddressRequest(

		@Size(min = 2, max = 100, message = "Title must be between 2 to 100 characters") String title,

		@Size(min = 2, max = 100, message = "Line 1 must be between 2 to 100 characters") String line1,

		@Size(min = 2, max = 100, message = "Line 1 must be between 2 to 100 characters") String line2,

		@Size(min = 2, max = 100, message = "Country must be between 2 to 100 characters") String country,

		@Size(min = 2, max = 100, message = "City must be between 2 to 100 characters") String city,

		@Size(min = 2, max = 15, message = "Postcode must be between 2 to 15 characters") String postcode) {

}
