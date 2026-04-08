package com.ecom.clothes.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecom.clothes.entity.ProductSku;

public interface ProductSkuRepository extends JpaRepository<ProductSku, Long> {

	@Query("SELECT ps FROM ProductSku ps WHERE ps.deletedAt IS NULL")
	Page<ProductSku> findAllActive(Pageable pageable);

	@Query("SELECT ps FROM ProductSku ps WHERE ps.deletedAt IS NOT NULL")
	Page<ProductSku> findAllDeleted(Pageable pageable);

	@Query("SELECT ps FROM ProductSku ps WHERE ps.deletedAt IS NULL AND LOWER(ps.sku) LIKE LOWER(CONCAT('%', :search, '%'))")
	Page<ProductSku> searchActiveBySku(String search, Pageable pageable);

	@Query("SELECT ps FROM ProductSku ps WHERE ps.id = :id AND ps.deletedAt IS NULL")
	Optional<ProductSku> findActiveById(Long id);
}
