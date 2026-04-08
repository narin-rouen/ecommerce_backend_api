package com.ecom.clothes.dto.response;

import java.math.BigDecimal;

import com.ecom.clothes.entity.ProductSku;

public record ProductSkuResponse(

		Long id, ProductResponse product, ProductAttributeResponse size, ProductAttributeResponse color, String sku,
		BigDecimal price, Integer quantity) {

	public static ProductSkuResponse from(ProductSku productSku) {
		return new ProductSkuResponse(productSku.getId(), ProductResponse.from(productSku.getProduct()),
				ProductAttributeResponse.from(productSku.getSizeAttributeId()),
				ProductAttributeResponse.from(productSku.getColorAttributeId()), productSku.getSku(),
				productSku.getPrice(), productSku.getQuantity());
	}
}
