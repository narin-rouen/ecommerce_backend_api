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
import com.ecom.clothes.dto.request.CreateProductRequest;
import com.ecom.clothes.dto.request.UpdateProductRequest;
import com.ecom.clothes.dto.response.ProductPageResponse;
import com.ecom.clothes.dto.response.ProductResponse;
import com.ecom.clothes.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

	private final ProductService productService;

	@GetMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ProductPageResponse> getAllProducts(@Valid PageRequest request) {
		log.info("Admin fetch all products with page: {}, size: {}", request.page(), request.size());
		ProductPageResponse response = productService.getAllProducts(request);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/search")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ProductPageResponse> searchAllProducts(@Valid PageRequest request) {
		log.info("Admin search products with keyword: {}", request.search());
		ProductPageResponse response = productService.searchAllProducts(request);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/deleted")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ProductPageResponse> getAllDeletedProducts(@Valid PageRequest request) {
		log.info("Admin find all deleted products with with page: {}, size: {}", request.page(), request.size());
		ProductPageResponse response = productService.getAllDeletedProducts(request);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
		log.info("Admin fetch product with id: {}", id);
		ProductResponse response = productService.getProductById(id);
		return ResponseEntity.ok(response);
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {
		log.info("Admin create new product with sucategory id: {}, name: {}", request.subcategoryId(), request.name());
		ProductResponse response = productService.createProduct(request);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
			@Valid @RequestBody UpdateProductRequest request) {
		log.info("Admin update product with id: {}", id);
		ProductResponse response = productService.updateProduct(id, request);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		log.info("Admin delete product with id: {}", id);
		productService.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}
}
