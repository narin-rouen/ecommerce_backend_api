package com.ecom.clothes.dto.response;

import com.ecom.clothes.entity.Address;

public record AddressResponse(UserSummaryResponse user, Long id, String title, String line1, String line2,
		String country, String city, String postcode) {

	public static AddressResponse from(Address address) {
		UserSummaryResponse userSummary = address.getUser() != null ? UserSummaryResponse.from(address.getUser())
				: null;

		return new AddressResponse(userSummary, address.getId(), address.getTitle(), address.getLine1(),
				address.getLine2(), address.getCountry(), address.getCity(), address.getPostcode());
	}
}
