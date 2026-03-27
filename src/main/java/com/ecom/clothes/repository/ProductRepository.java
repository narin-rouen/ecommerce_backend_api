package com.ecom.clothes.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecom.clothes.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Override
	@Query("SELECT p FROM Product p WHERE p.deletedAt IS NULL")
	Page<Product> findAll(Pageable pageable);

	@Override
	@Query("SELECT p FROM Product p WHERE p.id = :id AND p.deletedAt IS NULL")
	Optional<Product> findById(@Param("id") Long id);

	@Query("SELECT p FROM Product p WHERE p.deletedAt IS NULL AND (LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%')))")
	Page<Product> findAllWithSearch(@Param("search") String search, Pageable pageable);

	@Query("SELECT p FROM Product p WHERE p.deletedAt IS NOT NULL")
	Page<Product> findAllDeleted(Pageable pageable);
}
