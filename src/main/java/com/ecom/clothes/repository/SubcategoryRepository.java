package com.ecom.clothes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecom.clothes.entity.Subcategory;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

	@Query("SELECT s FROM Subcategory s WHERE " + "LOWER(s.name) LIKE LOWER(CONCAT('%', :search, '%')) OR "
			+ "LOWER(s.description) LIKE LOWER(CONCAT('%', :search, '%'))")
	Page<Subcategory> findAllWithSearch(@Param("search") String search, Pageable pageable);

}
