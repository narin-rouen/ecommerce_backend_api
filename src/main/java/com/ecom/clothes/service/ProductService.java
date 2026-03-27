package com.ecom.clothes.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.clothes.dto.common.PageRequest;
import com.ecom.clothes.dto.request.CreateProductRequest;
import com.ecom.clothes.dto.request.UpdateProductRequest;
import com.ecom.clothes.dto.response.ProductPageResponse;
import com.ecom.clothes.dto.response.ProductResponse;
import com.ecom.clothes.entity.Product;
import com.ecom.clothes.entity.Subcategory;
import com.ecom.clothes.exception.ProductNotFoundException;
import com.ecom.clothes.exception.SubcategoryNotFoundException;
import com.ecom.clothes.repository.ProductRepository;
import com.ecom.clothes.repository.SubcategoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

	private final ProductRepository productRepository;
	private final SubcategoryRepository subcategoryRepository;

	@Transactional(readOnly = true)
	public ProductPageResponse getAllProducts(PageRequest request) {
		log.info("Fetch all products with page: {}, size: {}", request.page(), request.size());

		Page<Product> productPage = productRepository.findAll(request.toPageable());

		List<ProductResponse> response = productPage.getContent().stream().map(ProductResponse::from).toList();

		return new ProductPageResponse(response, productPage.getNumber(), productPage.getSize(), request.sortBy(),
				request.direction(), request.search());
	}

	@Transactional(readOnly = true)
	public ProductPageResponse searchAllProducts(PageRequest request) {
		log.info("Search all products with search: {}", request.search());

		Page<Product> productPage = productRepository.findAllWithSearch(request.search(), request.toPageable());

		List<ProductResponse> response = productPage.getContent().stream().map(ProductResponse::from).toList();

		return new ProductPageResponse(response, productPage.getNumber(), productPage.getSize(), request.sortBy(),
				request.direction(), request.search());
	}

	@Transactional(readOnly = true)
	public ProductResponse getProductById(Long id) {
		log.info("Get product with id: {}", id);

		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ProductNotFoundException("Product not found with id: {}" + id));

		return ProductResponse.from(product);
	}

	@Transactional(readOnly = true)
	public ProductPageResponse getAllDeletedProducts(PageRequest request) {
		log.info("Fetch all delete products with page: {}, size: {}", request.page(), request.size());

		Page<Product> productPage = productRepository.findAllDeleted(request.toPageable());

		List<ProductResponse> response = productPage.getContent().stream().map(ProductResponse::from).toList();

		return new ProductPageResponse(response, productPage.getNumber(), productPage.getSize(), request.sortBy(),
				request.direction(), request.search());
	}

	@Transactional
	public ProductResponse createProduct(CreateProductRequest request) {
		log.info("Create product with name: {}", request.name());

		// check if subcateogry exists
		Subcategory subcategory = subcategoryRepository.findById(request.subcategoryId()).orElseThrow(
				() -> new SubcategoryNotFoundException("Category not found with id: {}" + request.subcategoryId()));

		Product product = new Product();
		product.setName(request.name());
		product.setDescription(request.description());
		product.setImageFileName(request.imageFileName());
		product.setSubcategory(subcategory);

		Product savedProduct = productRepository.save(product);
		log.info("Product saved successfully with id: {}", savedProduct.getId());

		return ProductResponse.from(savedProduct);
	}

	@Transactional
	public ProductResponse updateProduct(Long id, UpdateProductRequest request) {
		log.info("Update product with id: {}", id);

		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ProductNotFoundException("Product not found with id: {}" + id));

		if (request.name() != null) {
			product.setName(request.name());
		}

		if (request.description() != null) {
			product.setDescription(request.description());
		}

		if (request.imageFileName() != null) {
			product.setImageFileName(request.imageFileName());
		}

		if (request.subcategoryId() != null) {
			Subcategory subcategory = subcategoryRepository.findById(request.subcategoryId()).orElseThrow(
					() -> new SubcategoryNotFoundException("Category not found with id: {}" + request.subcategoryId()));

			product.setSubcategory(subcategory);
		}

		Product updatedProduct = productRepository.save(product);
		log.info("Product updated successfully with id: {}", id);

		return ProductResponse.from(updatedProduct);
	}

	@Transactional
	public void deleteProduct(Long id) {
		log.info("Delete product with id: {}", id);

		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ProductNotFoundException("Product not found with id: {}" + id));

		product.setDeletedAt(LocalDateTime.now());
		productRepository.save(product);

		log.info("Product soft deleted successfully with id: {}", id);
	}
}
