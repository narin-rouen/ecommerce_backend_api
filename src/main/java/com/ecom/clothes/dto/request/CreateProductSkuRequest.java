package com.ecom.clothes.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateProductSkuRequest(

		@NotNull(message = "Product ID is required") Long productId,

		@NotNull(message = "Size ID is required") Long sizeId,

		@NotNull(message = "Color ID is required") Long colorId,

		@NotBlank(message = "SKU is required")
		@Size(max = 50, message = "SKU must not exceed 50 characters") String sku,

		@NotNull(message = "Price is required")
		@DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0") BigDecimal price,

		@NotNull(message = "Quantity is required")
		@Min(value = 0, message = "Quantity must be non-negative") Integer quantity

) {

}
