package com.ecom.clothes.dto.response;

import java.util.List;

public record UserAddressResponse(

		UserSummaryResponse user, List<AddressResponse> addresses) {

}
