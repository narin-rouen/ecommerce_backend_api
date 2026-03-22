package com.ecom.clothes.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.clothes.dto.common.PageRequest;
import com.ecom.clothes.dto.request.CreateSubcategoryRequest;
import com.ecom.clothes.dto.request.UpdateSubcategoryRequest;
import com.ecom.clothes.dto.response.SubcategoryPageResponse;
import com.ecom.clothes.dto.response.SubcategoryResponse;
import com.ecom.clothes.entity.Category;
import com.ecom.clothes.entity.Subcategory;
import com.ecom.clothes.exception.CategoryNotFoundException;
import com.ecom.clothes.exception.SubcategoryNotFoundException;
import com.ecom.clothes.repository.CategoryRepository;
import com.ecom.clothes.repository.SubcategoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubcategoryService {

	private final SubcategoryRepository subcategoryRepository;
	private final CategoryRepository categoryRepository;

	@Transactional(readOnly = true)
	public SubcategoryPageResponse getAllSubcategory(PageRequest request) {
		log.info("Fetch all subcategory with page: {}, size:{} ", request.page(), request.size());

		Page<Subcategory> subcategoryPage = subcategoryRepository.findAll(request.toPageable());

		List<SubcategoryResponse> subcategoryResponse = subcategoryPage.getContent().stream()
				.map(SubcategoryResponse::from).toList();

		return new SubcategoryPageResponse(subcategoryResponse, subcategoryPage.getNumber(), subcategoryPage.getSize(),
				request.sortBy(), request.direction(), request.search());
	}

	@Transactional(readOnly = true)
	public SubcategoryPageResponse search(PageRequest request) {
		log.info("Search all category with keyword: {}", request.search());

		Page<Subcategory> subcategoryPage = subcategoryRepository.findAllWithSearch(request.search(),
				request.toPageable());

		List<SubcategoryResponse> subcategoryResponse = subcategoryPage.getContent().stream()
				.map(SubcategoryResponse::from).toList();

		return new SubcategoryPageResponse(subcategoryResponse, subcategoryPage.getNumber(), subcategoryPage.getSize(),
				request.sortBy(), request.direction(), request.search());
	}

	@Transactional(readOnly = true)
	public SubcategoryResponse getSubcategoryById(Long id) {
		log.info("Get subcategory by id: {}", id);

		Subcategory subcategory = subcategoryRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Subategory not found with id: {} " + id));

		return SubcategoryResponse.from(subcategory);
	}

	@Transactional
	public SubcategoryResponse createSubcategory(CreateSubcategoryRequest request) {
		log.info("Create new subcategory with category id: {}, name: {}", request.categoryId(), request.name());

		// check if the category id exist
		Category category = categoryRepository.findById(request.categoryId()).orElseThrow(
				() -> new CategoryNotFoundException("Category not found with id: {}" + request.categoryId()));

		Subcategory subcategory = new Subcategory();
		subcategory.setCategory(category);
		subcategory.setName(request.name());
		subcategory.setDescription(request.description());

		Subcategory savedSubcategory = subcategoryRepository.save(subcategory);
		log.info("Subcategory saved successfully with id: {}", savedSubcategory.getId());

		return SubcategoryResponse.from(savedSubcategory);
	}

	@Transactional
	public SubcategoryResponse updateSubcategory(Long id, UpdateSubcategoryRequest request) {
		log.info("Update subcategory with id:{}", id);

		Subcategory subcategory = subcategoryRepository.findById(id)
				.orElseThrow(() -> new SubcategoryNotFoundException("Subcategory not found with id: {}" + id));

		if (request.categoryId() != null) {
			Category category = categoryRepository.findById(request.categoryId()).orElseThrow(
					() -> new CategoryNotFoundException("Category not found with id: {}" + request.categoryId()));
			subcategory.setCategory(category);
		}
		if (request.name() != null) {
			subcategory.setName(request.name());
		}
		if (request.description() != null) {

		}

		Subcategory updatedSubcategory = subcategoryRepository.save(subcategory);
		log.info("Update subcategory successfully with id: {}", updatedSubcategory.getId());

		return SubcategoryResponse.from(updatedSubcategory);
	}

	@Transactional
	public void deleteSubcategory(Long id) {
		log.info("Delete subcategory with id: {}", id);

		Subcategory subcategory = subcategoryRepository.findById(id)
				.orElseThrow(() -> new SubcategoryNotFoundException("Subcategory not found with id: {}" + id));

		subcategory.setDeletedAt(LocalDateTime.now());
		subcategoryRepository.save(subcategory);

		log.info("Subcategory soft delete successfully with id: {}", id);
	}
}
