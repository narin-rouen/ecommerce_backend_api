package com.ecom.clothes.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "subcategories")
@Data
public class Subcategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;

	@NotBlank(message = "Subcategory name is required")
	@Column(nullable = false, unique = true, length = 100)
	@Size(min = 2, max = 100, message = "Subcategory name must be between 2 to 100 characters")
	private String name;

	@NotBlank(message = "Subcategory description is required")
	@Column(nullable = false, length = 500)
	@Size(min = 2, max = 500, message = "Subcategory description must be between 2 to 500 characters")
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
