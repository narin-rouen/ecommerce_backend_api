package com.ecom.clothes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecom.clothes.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	@Query("SELECT c FROM Category c WHERE " + "(:search IS NULL OR :search = '' OR "
			+ "LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%')) OR "
			+ "LOWER(c.description) LIKE LOWER(CONCAT('%', :search, '%'))) AND " + "c.deletedAt IS NULL")
	Page<Category> findAllWithSearch(@Param("search") String search, Pageable pageable);
}
