package com.ecom.clothes.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.clothes.dto.common.PageRequest;
import com.ecom.clothes.dto.request.CreateCategoryRequest;
import com.ecom.clothes.dto.request.UpdateCategoryRequest;
import com.ecom.clothes.dto.response.CategoryDetailsResponse;
import com.ecom.clothes.dto.response.CategoryPageResponse;
import com.ecom.clothes.dto.response.CategoryResponse;
import com.ecom.clothes.dto.response.SubcategoryResponse;
import com.ecom.clothes.entity.Category;
import com.ecom.clothes.exception.CategoryNotFoundException;
import com.ecom.clothes.repository.CategoryRepository;
import com.ecom.clothes.repository.SubcategoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

	private final CategoryRepository categoryRepository;
	private final SubcategoryRepository subcategoryRepository;

	@Transactional(readOnly = true)
	public CategoryPageResponse getAllCategories(PageRequest request) {
		log.info("Fetching all categories with page: {}, size: {}", request.page(), request.size());

		Page<Category> categoryPage;
		if (request.search() != null && !request.search().isEmpty()) {
			categoryPage = categoryRepository.findAllWithSearch(request.search(), request.toPageable());
		} else {
			categoryPage = categoryRepository.findAll(request.toPageable());
		}

		List<CategoryResponse> categoryResponses = categoryPage.getContent().stream().map(CategoryResponse::from)
				.toList();

		return new CategoryPageResponse(categoryResponses, categoryPage.getNumber(), categoryPage.getSize(),
				request.sortBy(), request.direction(), request.search());
	}

	@Transactional(readOnly = true)
	public CategoryDetailsResponse getCategoryById(Long id) {
		log.info("Fetching category by id: {}", id);

		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));

		List<SubcategoryResponse> subcategoryResponses = subcategoryRepository.findByCategoryId(category.getId())
				.stream().map(SubcategoryResponse::from).collect(Collectors.toList());

		return CategoryDetailsResponse.from(category, subcategoryResponses);
	}

	@Transactional
	public CategoryResponse createCategory(CreateCategoryRequest request) {
		log.info("Creating new category with name: {}", request.name());

		Category category = new Category();
		category.setName(request.name());
		category.setDescription(request.description());

		Category savedCategory = categoryRepository.save(category);
		log.info("Category created successfully with id: {}", savedCategory.getId());

		return CategoryResponse.from(savedCategory);
	}

	@Transactional
	public CategoryResponse updateCategory(Long id, UpdateCategoryRequest request) {
		log.info("Updating category with id: {}", id);

		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));

		category.setName(request.name());
		category.setDescription(request.description());

		Category updatedCategory = categoryRepository.save(category);
		log.info("Category updated successfully with id: {}", updatedCategory.getId());

		return CategoryResponse.from(updatedCategory);
	}

	public void deleteCategory(Long id) {
		log.info("Soft deleting category with id: {}", id);

		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));

		category.setDeletedAt(LocalDateTime.now());
		categoryRepository.save(category);

		log.info("Category soft deleted successfully with id: {}", id);
	}
}
