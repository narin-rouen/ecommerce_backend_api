package com.ecom.clothes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.clothes.entity.ProductSku;

public interface ProductSkuRepository extends JpaRepository<ProductSku, Long> {

}
