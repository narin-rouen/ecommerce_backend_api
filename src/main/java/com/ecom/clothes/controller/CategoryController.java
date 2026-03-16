package com.ecom.clothes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.clothes.dto.common.PageRequest;
import com.ecom.clothes.dto.request.CreateCategoryRequest;
import com.ecom.clothes.dto.request.UpdateCategoryRequest;
import com.ecom.clothes.dto.response.CategoryPageResponse;
import com.ecom.clothes.dto.response.CategoryResponse;
import com.ecom.clothes.service.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

	private final CategoryService categoryService;

	@GetMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<CategoryPageResponse> getAllCategory(@Valid PageRequest pageRequest) {
		log.info("REST request to get all categories with page: {}, size: {}", pageRequest.page(), pageRequest.size());
		CategoryPageResponse response = categoryService.getAllCategories(pageRequest);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
		log.info("REST request to get category by id: {}", id);
		CategoryResponse response = categoryService.getCategoryById(id);
		return ResponseEntity.ok(response);
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<CategoryResponse> creatCategory(@Valid @RequestBody CreateCategoryRequest request) {
		log.info("REST request to create category: {}", request.name());
		CategoryResponse response = categoryService.createCategory(request);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id,
			@Valid @RequestBody UpdateCategoryRequest request) {
		log.info("REST request to update category with id: {}", id);
		CategoryResponse response = categoryService.updateCategory(id, request);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
		log.info("REST request to delete category with id: {}", id);

		categoryService.deleteCategory(id);
		return ResponseEntity.noContent().build();
	}

}
