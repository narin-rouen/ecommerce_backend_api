package com.ecom.clothes.dto.request;

import java.math.BigDecimal;

public record CreatePaymentRequest(BigDecimal amount, String provider) {

}
