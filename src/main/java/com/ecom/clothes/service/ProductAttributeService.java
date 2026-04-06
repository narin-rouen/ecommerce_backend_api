package com.ecom.clothes.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.clothes.dto.common.PageRequest;
import com.ecom.clothes.dto.request.CreateProductAttributeRequest;
import com.ecom.clothes.dto.request.UpdateProductAttributeRequest;
import com.ecom.clothes.dto.response.ProductAttributePageResponse;
import com.ecom.clothes.dto.response.ProductAttributeResponse;
import com.ecom.clothes.entity.ProductAttribute;
import com.ecom.clothes.repository.ProductAttributeRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProductAttributeService {

	private final ProductAttributeRepository productAttributeRepository;

	@Transactional(readOnly = true)
	public ProductAttributePageResponse getAllActiveAttributes(PageRequest request) {
		log.info("Fetching all active product attributes with pagination: {}", request);

		Page<ProductAttribute> attributePage;
		attributePage = productAttributeRepository.findAllActive(request.toPageable());

		List<ProductAttributeResponse> attributeResponses = attributePage.getContent().stream()
				.map(ProductAttributeResponse::from).toList();

		return new ProductAttributePageResponse(attributeResponses, attributePage.getNumber(), attributePage.getSize(),
				request.sortBy(), request.direction(), request.search());
	}

	@Transactional(readOnly = true)
	public ProductAttributePageResponse getAllDeletedAttributes(PageRequest request) {
		log.info("Fetching all deleted product attributes with pagination: {}", request);

		Page<ProductAttribute> attributePage;
		attributePage = productAttributeRepository.findAllDeleted(request.toPageable());

		List<ProductAttributeResponse> attributeResponses = attributePage.getContent().stream()
				.map(ProductAttributeResponse::from).toList();

		return new ProductAttributePageResponse(attributeResponses, attributePage.getNumber(), attributePage.getSize(),
				request.sortBy(), request.direction(), request.search());
	}

	@Transactional(readOnly = true)
	public ProductAttributeResponse getActiveAttributeById(Long id) {
		log.info("Fetching active product attribute by id: {}", id);

		ProductAttribute attribute = productAttributeRepository.findActiveById(id)
				.orElseThrow(() -> new RuntimeException("Active product attribute not found with id: " + id));

		return ProductAttributeResponse.from(attribute);
	}

	@Transactional
	public ProductAttributeResponse createAttribute(CreateProductAttributeRequest request) {
		log.info("Creating new product attribute with name: {}", request.value());

		ProductAttribute attribute = new ProductAttribute();
		attribute.setType(request.type());
		attribute.setValue(request.value());

		ProductAttribute savedAttribute = productAttributeRepository.save(attribute);
		log.info("Product attribute created successfully with id: {}", savedAttribute.getId());

		return ProductAttributeResponse.from(savedAttribute);
	}

	@Transactional
	public ProductAttributeResponse updateAttrbute(Long id, UpdateProductAttributeRequest request) {
		log.info("Updating product attribute with id: {}", id);

		ProductAttribute attribute = productAttributeRepository.findActiveById(id)
				.orElseThrow(() -> new RuntimeException("Active product attribute not found with id: " + id));

		if (request.value() != null) {
			attribute.setValue(request.value());
		}

		ProductAttribute updatedAttribute = productAttributeRepository.save(attribute);
		log.info("Product attribute updated successfully with id: {}", updatedAttribute.getId());

		return ProductAttributeResponse.from(updatedAttribute);
	}

	@Transactional
	public void softDeleteAttribute(Long id) {
		log.info("Soft deleting product attribute with id: {}", id);

		ProductAttribute attribute = productAttributeRepository.findActiveById(id)
				.orElseThrow(() -> new RuntimeException("Active product attribute not found with id: " + id));

		attribute.setDeletedAt(java.time.LocalDateTime.now());
		productAttributeRepository.save(attribute);

		log.info("Product attribute soft deleted successfully with id: {}", id);

	}
}
