package com.ecom.clothes.dto.request;

import com.ecom.clothes.entity.ProductAttributeType;

public record CreateProductAttributeRequest(

		ProductAttributeType type, String value) {

}
