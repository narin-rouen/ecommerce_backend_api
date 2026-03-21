package com.ecom.clothes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.clothes.entity.Subcategory;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

	Page<Subcategory> findAllWithSearch(String search, Pageable pageable);

}
