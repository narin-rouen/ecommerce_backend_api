package com.ecom.clothes.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateCartItemRequest(@JsonProperty("product_sku_id") Long productSkuId) {

}
