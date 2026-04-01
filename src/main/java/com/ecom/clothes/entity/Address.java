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
@Table(name = "addresses")
@Data
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@NotBlank(message = "Title is required")
	@Column(nullable = false, length = 100)
	@Size(min = 2, max = 100, message = "Title must be between 2 to 100 characters")
	private String title;

	@NotBlank(message = "Line 1 is required")
	@Column(name = "line_1", nullable = false, length = 100)
	@Size(min = 2, max = 100, message = "Line 1 must be between 2 to 100 characters")
	private String line1;

	@Column(name = "line_2", nullable = true, length = 100)
	@Size(min = 2, max = 100, message = "Line 1 must be between 2 to 100 characters")
	private String line2;

	@NotBlank(message = "Country is required")
	@Column(nullable = false, length = 100)
	@Size(min = 2, max = 100, message = "Country must be between 2 to 100 characters")
	private String country;

	@NotBlank(message = "City is required")
	@Column(nullable = false, length = 100)
	@Size(min = 2, max = 100, message = "City must be between 2 to 100 characters")
	private String city;

	@NotBlank(message = "Postcode is required")
	@Column(nullable = false, length = 15)
	@Size(min = 2, max = 15, message = "Postcode must be between 2 to 15 characters")
	private String postcode;

	@Column(name = "created_at", nullable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false)
	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@Column(name = "deleted_at", nullable = true)
	private LocalDateTime deletedAt;
}
