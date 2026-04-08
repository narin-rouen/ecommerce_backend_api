package com.ecom.clothes.dto.request;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record UpdateProductSkuRequest(

		@JsonProperty("size_id") Long sizeId,

		@JsonProperty("color_id") Long colorId,

		@Size(max = 50, message = "SKU must not exceed 50 characters") String sku,

		@DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0") BigDecimal price,

		@Min(value = 0, message = "Quantity must be non-negative") Integer quantity) {

}
