package com.ecom.clothes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.clothes.dto.common.PageRequest;
import com.ecom.clothes.dto.request.CreateProductAttributeRequest;
import com.ecom.clothes.dto.request.UpdateProductAttributeRequest;
import com.ecom.clothes.dto.response.ProductAttributePageResponse;
import com.ecom.clothes.dto.response.ProductAttributeResponse;
import com.ecom.clothes.service.ProductAttributeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/admin/attributes")
@RequiredArgsConstructor
@Slf4j
public class ProductAttributeController {

	private final ProductAttributeService productAttributeService;

	@GetMapping
	public ResponseEntity<ProductAttributePageResponse> getAllActiveAttributes(@Valid PageRequest request) {
		log.info("Admin requested to get all active product attributes with page: {}, size: {}", request.page(),
				request.size());
		ProductAttributePageResponse response = productAttributeService.getAllActiveAttributes(null);
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<ProductAttributePageResponse> getAllDeletedAttributes(@Valid PageRequest request) {
		log.info("Admin requested to get all deleted product attributes with page: {}, size: {}", request.page(),
				request.size());
		ProductAttributePageResponse response = productAttributeService.getAllDeletedAttributes(null);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductAttributeResponse> getActiveAttributeById(@PathVariable Long id) {
		log.info("Admin requested to get active product attribute by id: {}", id);
		ProductAttributeResponse response = productAttributeService.getActiveAttributeById(id);
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<ProductAttributeResponse> createAttribute(
			@Valid @RequestBody CreateProductAttributeRequest request) {
		log.info("Admin requested to create product attribute: {}", request);
		ProductAttributeResponse response = productAttributeService.createAttribute(request);
		return ResponseEntity.ok(response);
	}

	@PutMapping
	public ResponseEntity<ProductAttributeResponse> updateAttribute(@PathVariable Long id,
			@Valid @RequestBody UpdateProductAttributeRequest request) {
		log.info("Admin requested to update product attribute with id: {}, data: {}", id, request);
		ProductAttributeResponse response = productAttributeService.updateAttrbute(id, request);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAttribute(@PathVariable Long id) {
		log.info("Admin requested to delete product attribute with id: {}", id);
		productAttributeService.softDeleteAttribute(id);
		return ResponseEntity.noContent().build();
	}
}
