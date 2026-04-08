package com.ecom.clothes.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.clothes.dto.common.PageRequest;
import com.ecom.clothes.dto.request.CreateProductSkuRequest;
import com.ecom.clothes.dto.request.UpdateProductSkuRequest;
import com.ecom.clothes.dto.response.ProductSkuPageResponse;
import com.ecom.clothes.dto.response.ProductSkuResponse;
import com.ecom.clothes.entity.Product;
import com.ecom.clothes.entity.ProductAttribute;
import com.ecom.clothes.entity.ProductSku;
import com.ecom.clothes.exception.ProductAttributeNotFoundException;
import com.ecom.clothes.exception.ProductNotFoundException;
import com.ecom.clothes.exception.ProductSkuNotFoundException;
import com.ecom.clothes.repository.ProductAttributeRepository;
import com.ecom.clothes.repository.ProductRepository;
import com.ecom.clothes.repository.ProductSkuRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductSkuService {

	private final ProductSkuRepository productSkuRepository;
	private final ProductRepository productRepository;
	private final ProductAttributeRepository productAttributeRepository;

	@Transactional(readOnly = true)
	public ProductSkuPageResponse getAllActiveProdcutSkus(PageRequest request) {
		log.info("Fetching all active product SKUs with page: {}, size: {}", request.page(), request.size());

		var productSkuPage = productSkuRepository.findAllActive(request.toPageable());

		var productSkuResponses = productSkuPage.getContent().stream().map(ProductSkuResponse::from).toList();

		return new ProductSkuPageResponse(productSkuResponses, productSkuPage.getNumber(), productSkuPage.getSize(),
				request.sortBy(), request.direction(), request.search());
	}

	@Transactional(readOnly = true)
	public ProductSkuPageResponse getAllDeletedProdcutSkus(PageRequest request) {
		log.info("Fetching all deleted product SKUs with page: {}, size: {}", request.page(), request.size());

		var productSkuPage = productSkuRepository.findAllDeleted(request.toPageable());

		var productSkuResponses = productSkuPage.getContent().stream().map(ProductSkuResponse::from).toList();

		return new ProductSkuPageResponse(productSkuResponses, productSkuPage.getNumber(), productSkuPage.getSize(),
				request.sortBy(), request.direction(), request.search());
	}

	@Transactional(readOnly = true)
	public ProductSkuPageResponse searchAllActiveProdcutSkus(PageRequest request) {
		log.info("Searching active product SKUs with page: {}, size: {}, search: {}", request.page(), request.size(),
				request.search());

		var productSkuPage = productSkuRepository.searchActiveBySku(request.search(), request.toPageable());

		var productSkuResponses = productSkuPage.getContent().stream().map(ProductSkuResponse::from).toList();

		return new ProductSkuPageResponse(productSkuResponses, productSkuPage.getNumber(), productSkuPage.getSize(),
				request.sortBy(), request.direction(), request.search());
	}

	@Transactional(readOnly = true)
	public ProductSkuResponse getActiveProductSkuById(Long id) {
		log.info("Fetching active product SKU by id: {}", id);

		var productSku = productSkuRepository.findActiveById(id)
				.orElseThrow(() -> new ProductSkuNotFoundException("Product SKU not found with id: " + id));

		return ProductSkuResponse.from(productSku);
	}

	@Transactional
	public ProductSkuResponse createProductSku(CreateProductSkuRequest request) {
		log.info("Creating new product SKU with product id: {}, size attribute id: {}, color attribute id: {}, sku: {}",
				request.productId(), request.sizeId(), request.colorId(), request.sku());

		// Validate product, size attribute, and color attribute existence
		Product product = productRepository.findById(request.productId())
				.orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + request.productId()));

		ProductAttribute sizeAttribute = productAttributeRepository.findActiveById(request.sizeId()).orElseThrow(
				() -> new ProductAttributeNotFoundException("Size attribute not found with id: " + request.sizeId()));

		ProductAttribute colorAttribute = productAttributeRepository.findActiveById(request.colorId()).orElseThrow(
				() -> new ProductAttributeNotFoundException("Color attribute not found with id: " + request.colorId()));

		var productSku = new ProductSku();
		productSku.setProduct(product);
		productSku.setSizeAttributeId(sizeAttribute);
		productSku.setColorAttributeId(colorAttribute);
		productSku.setSku(request.sku());
		productSku.setPrice(request.price());
		productSku.setQuantity(request.quantity());

		var savedProductSku = productSkuRepository.save(productSku);
		log.info("Product SKU created successfully with id: {}", savedProductSku.getId());

		return ProductSkuResponse.from(savedProductSku);
	}

	@Transactional
	public ProductSkuResponse updateProductSku(Long id, UpdateProductSkuRequest request) {
		log.info("Updating product SKU with id: {}", id);

		var productSku = productSkuRepository.findActiveById(id)
				.orElseThrow(() -> new ProductSkuNotFoundException("Product SKU not found with id: " + id));

		// Validate product, size attribute, and color attribute existence
		if (request.sizeId() != null) {
			ProductAttribute sizeAttribute = productAttributeRepository.findActiveById(request.sizeId())
					.orElseThrow(() -> new ProductAttributeNotFoundException(
							"Size attribute not found with id: " + request.sizeId()));
			productSku.setSizeAttributeId(sizeAttribute);
		}

		if (request.colorId() != null) {
			ProductAttribute colorAttribute = productAttributeRepository.findActiveById(request.colorId())
					.orElseThrow(() -> new ProductAttributeNotFoundException(
							"Color attribute not found with id: " + request.colorId()));
			productSku.setColorAttributeId(colorAttribute);
		}

		if (request.sku() != null) {
			productSku.setSku(request.sku());
		}

		if (request.price() != null) {
			productSku.setPrice(request.price());
		}

		if (request.quantity() != null) {
			Integer newQuantity = request.quantity();
			Integer existingQuantity = productSku.getQuantity();
			productSku.setQuantity(existingQuantity + newQuantity);
		}

		var updatedProductSku = productSkuRepository.save(productSku);
		log.info("Product SKU updated successfully with id: {}", updatedProductSku.getId());

		return ProductSkuResponse.from(updatedProductSku);
	}

	@Transactional
	public void deleteProductSku(Long id) {
		log.info("Deleting product SKU with id: {}", id);

		var productSku = productSkuRepository.findActiveById(id)
				.orElseThrow(() -> new ProductSkuNotFoundException("Product SKU not found with id: " + id));

		productSku.setDeletedAt(LocalDateTime.now());
		productSkuRepository.save(productSku);
		log.info("Product SKU deleted successfully with id: {}", id);
	}
}
