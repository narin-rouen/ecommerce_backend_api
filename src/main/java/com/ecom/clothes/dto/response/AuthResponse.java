package com.ecom.clothes.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record AuthResponse(

		@JsonProperty("access_token") String accessToken, @JsonProperty("refresh_token") String refreshToken,
		@JsonProperty("expires_in") String expiresIn, @JsonProperty("refresh_in") String refreshIn,
		@JsonProperty("token_type") String tokenType, UserSummaryResponse user) {

}
