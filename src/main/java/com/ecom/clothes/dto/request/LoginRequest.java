package com.ecom.clothes.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

		@NotBlank(message = "Email is reqiured!")
		@Email(message = "Invalid email format")
		@JsonProperty("email") String email,

		@NotBlank(message = "Password is reqiured!") @JsonProperty("password") String password) {

}
