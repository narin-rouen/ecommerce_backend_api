package com.ecom.clothes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecom.clothes.entity.Subcategory;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

	@Override
	@Query("SELECT s FROM Subcategory s WHERE s.deletedAt IS NULL")
	Page<Subcategory> findAll(Pageable pageable);

	@Override
	@Query("SELECT s FROM Subcategory s WHERE s.id = :id AND s.deletedAt IS NULL")
	Optional<Subcategory> findById(@Param("id") Long id);

	@Query("SELECT s FROM Subcategory s WHERE s.deletedAt IS NULL AND (LOWER(s.name) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(s.description) LIKE LOWER(CONCAT('%', :search, '%')))")
	Page<Subcategory> findAllWithSearch(@Param("search") String search, Pageable pageable);

	@Query("SELECT s FROM Subcategory s WHERE s.deletedAt IS NOT NULL")
	Page<Subcategory> findAllDeleted(Pageable pageable);

	List<Subcategory> findByCategoryId(Long categoryId);
}
