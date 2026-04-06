package com.ecom.clothes.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecom.clothes.entity.ProductAttribute;

public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {

	@Query("SELECT pa FROM ProductAttribute pa WHERE pa.deletedAt IS NULL")
	Page<ProductAttribute> findAllActive(Pageable pageable);

	@Query("SELECT pa FROM ProductAttribute pa WHERE pa.deletedAt IS NOT NULL")
	Page<ProductAttribute> findAllDeleted(Pageable pageable);

	@Query("SELECT pa FROM ProductAttribute pa WHERE pa.id = :id AND pa.deletedAt IS NULL")
	Optional<ProductAttribute> findActiveById(Long id);
}
