package com.ecom.clothes.dto.response;

import com.ecom.clothes.entity.User;
import com.ecom.clothes.entity.UserRole;

import lombok.Builder;

@Builder
public record UserSummaryResponse(long id, String firstName, String lastName, String email, UserRole role) {

	public static UserSummaryResponse from(User user) {
		return new UserSummaryResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),
				user.getRole());
	}
}
