package com.ecom.clothes.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record UpdateProductSkuRequest(

		Long sizeId, Long colorId,

		@Size(max = 50, message = "SKU must not exceed 50 characters") String sku,

		@DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0") BigDecimal price,

		@Min(value = 0, message = "Quantity must be non-negative") Integer quantity) {

}
