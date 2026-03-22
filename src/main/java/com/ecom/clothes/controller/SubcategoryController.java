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
import com.ecom.clothes.dto.request.CreateSubcategoryRequest;
import com.ecom.clothes.dto.request.UpdateSubcategoryRequest;
import com.ecom.clothes.dto.response.SubcategoryPageResponse;
import com.ecom.clothes.dto.response.SubcategoryResponse;
import com.ecom.clothes.service.SubcategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/admin/subcategories")
@Slf4j
@RequiredArgsConstructor
public class SubcategoryController {

	private final SubcategoryService subcategoryService;

	@GetMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<SubcategoryPageResponse> getAllSubcategory(@Valid PageRequest request) {
		log.info("Admin fetch all subcategory with page: {}, size: {}", request.page(), request.size());
		SubcategoryPageResponse response = subcategoryService.getAllSubcategory(request);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/search")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<SubcategoryPageResponse> search(@Valid PageRequest request) {
		log.info("Admin search subcategory with keyword: {}", request.search());
		SubcategoryPageResponse response = subcategoryService.search(request);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<SubcategoryResponse> getCategoryById(@PathVariable Long id) {
		log.info("Admin fetch subcategory with id: {}", id);
		SubcategoryResponse response = subcategoryService.getSubcategoryById(id);
		return ResponseEntity.ok(response);
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<SubcategoryResponse> createSubcategory(@Valid @RequestBody CreateSubcategoryRequest request) {
		log.info("Admin create new subcategory with category id: {}, name: {}", request.categoryId(), request.name());
		SubcategoryResponse response = subcategoryService.createSubcategory(request);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<SubcategoryResponse> updateSubcategory(@PathVariable Long id,
			@Valid @RequestBody UpdateSubcategoryRequest request) {
		log.info("Admin update subcategory with id: {}", id);
		SubcategoryResponse response = subcategoryService.updateSubcategory(id, request);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Void> deleteSubcategory(@PathVariable Long id) {
		log.info("Admin delete subcategory with id: {}", id);
		subcategoryService.deleteSubcategory(id);
		return ResponseEntity.noContent().build();
	}
}
