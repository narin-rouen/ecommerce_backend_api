package com.ecom.clothes.dto.request;

import com.ecom.clothes.entity.UserRole;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

		@NotBlank(message = "First name is required")
		@Size(min = 2, max = 100, message = "First name must be between 2 to 100 charaters")
		@JsonProperty("first_name") String firstName,

		@NotBlank(message = "Last name is required")
		@Size(min = 2, max = 100, message = "Last name must be between 2 to 100 charaters")
		@JsonProperty("last_name") String lastName,

		@NotBlank(message = "Username is required")
		@Size(min = 2, max = 100, message = "Usernam must be between 2 to 100 charaters")
		@JsonProperty("username") String username,

		@NotBlank(message = "Email is reqiured!")
		@Email(message = "Invalid email format")
		@JsonProperty("email") String email,

		@NotBlank(message = "Password is reqiured!")
		@Size(min = 6, message = "Password must be at least 6 charaters.")
		@JsonProperty("password") String password,

		@NotBlank(message = "Phone number is required") @JsonProperty("phone_number") String phoneNumber,

		@JsonProperty("role") UserRole role

) {
	public RegisterRequest {
		if (role == null) {
			role = UserRole.USER;
		}
	}
}
