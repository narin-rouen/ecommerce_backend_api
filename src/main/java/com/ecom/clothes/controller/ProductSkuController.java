package com.ecom.clothes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.clothes.dto.common.PageRequest;
import com.ecom.clothes.dto.request.CreateProductSkuRequest;
import com.ecom.clothes.dto.request.UpdateProductSkuRequest;
import com.ecom.clothes.dto.response.ProductSkuPageResponse;
import com.ecom.clothes.dto.response.ProductSkuResponse;
import com.ecom.clothes.service.ProductSkuService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductSkuController {

	private final ProductSkuService productSkuService;

	@GetMapping("/api/admin/skus")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ProductSkuPageResponse> getAllActiveProductSkusForAdmin(@Valid PageRequest request) {
		log.info("Admin requested all active product SKUs with page: {}, size: {}", request.page(), request.size());
		ProductSkuPageResponse response = productSkuService.getAllActiveProdcutSkus(request);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/api/admin/skus/deleted")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ProductSkuPageResponse> getAllDeletedProductSkusForAdmin(@Valid PageRequest request) {
		log.info("Admin requested all deleted product SKUs with page: {}, size: {}", request.page(), request.size());
		ProductSkuPageResponse response = productSkuService.getAllDeletedProdcutSkus(request);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/api/admin/skus/search")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ProductSkuPageResponse> searchAllActiveProductSkusForAdmin(@Valid PageRequest request) {
		log.info("Admin searched active product SKUs with page: {}, size: {}, search: {}", request.page(),
				request.size(), request.search());
		ProductSkuPageResponse response = productSkuService.searchAllActiveProdcutSkus(request);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/api/admin/skus/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ProductSkuResponse> getProductSkuByIdForAdmin(@PathVariable Long id) {
		log.info("Admin requested product SKU with id: {}", id);
		ProductSkuResponse response = productSkuService.getActiveProductSkuById(id);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/api/admin/skus")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ProductSkuResponse> createProductSkuForAdmin(
			@Valid @RequestBody CreateProductSkuRequest request) {
		log.info(
				"Admin requested to create product SKU with productId: {}, sizeAttributeId: {}, colorAttributeId: {}, sku: {}, price: {}, quantity: {}",
				request.productId(), request.sizeId(), request.colorId(), request.sku(), request.price(),
				request.quantity());
		ProductSkuResponse response = productSkuService.createProductSku(request);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/api/admin/skus/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ProductSkuResponse> updateProductSkuForAdmin(@PathVariable Long id,
			@Valid @RequestBody UpdateProductSkuRequest request) {
		log.info("Admin requested to update product SKU with id: {}", id);
		ProductSkuResponse response = productSkuService.updateProductSku(id, request);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/api/admin/skus/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteProductSkuForAdmin(@PathVariable Long id) {
		log.info("Admin requested to delete product SKU with id: {}", id);
		productSkuService.deleteProductSku(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/api/user/skus")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<ProductSkuPageResponse> getAllActiveProductSkusForUser(@Valid PageRequest request) {
		log.info("User requested all active product SKUs with page: {}, size: {}", request.page(), request.size());
		ProductSkuPageResponse response = productSkuService.getAllActiveProdcutSkus(request);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/api/user/skus/search")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<ProductSkuPageResponse> searchAllActiveProductSkusForUser(@Valid PageRequest request) {
		log.info("User searched active product SKUs with page: {}, size: {}, search: {}", request.page(),
				request.size(), request.search());
		ProductSkuPageResponse response = productSkuService.searchAllActiveProdcutSkus(request);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/api/user/skus/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<ProductSkuResponse> getProductSkuByIdForUser(@PathVariable Long id) {
		log.info("User requested product SKU with id: {}", id);
		ProductSkuResponse response = productSkuService.getActiveProductSkuById(id);
		return ResponseEntity.ok(response);
	}
}
