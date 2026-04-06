package com.ecom.clothes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.clothes.entity.ProductAttribute;

public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {

}
