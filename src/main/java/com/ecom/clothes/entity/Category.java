package com.ecom.clothes.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "categories")
@Data
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Category name is required")
	@Column(nullable = false, unique = true, length = 100)
	@Size(min = 2, max = 100, message = "Category name must be between 2 to 100 characters")
	private String name;

	@NotBlank(message = "Category description is required")
	@Column(nullable = false, length = 500)
	@Size(min = 2, max = 500, message = "Category description must be between 2 to 500 characters")
	private String description;

	@Column(name = "created_at", nullable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false)
	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@Column(name = "deleted_at", nullable = true)
	private LocalDateTime deletedAt;
}
