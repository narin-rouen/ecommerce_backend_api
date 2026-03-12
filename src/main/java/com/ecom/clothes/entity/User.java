package com.ecom.clothes.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "first_name", nullable = false)
	@Size(min = 2, max = 100, message = "First name must be between 2 to 100 characters")
	private String firstName;

	@Column(name = "last_name", nullable = false)
	@Size(min = 2, max = 100, message = "Last name must be between 2 to 100 characters")
	private String lastName;

	@Column(nullable = false, unique = true)
	@Size(min = 2, max = 100, message = "Username must be between 2 to 100 characters")
	private String username;

	@Column(nullable = false, unique = true, length = 100)
	@Email(message = "Email must be valid.")
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;

	@Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number format")
	@Column(name = "phone_number", nullable = false, unique = true, length = 15)
	private String phoneNumber;

	@Column(name = "profile_file_name", length = 250, nullable = true)
	private String profileFileName;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private UserRole role = UserRole.USER;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private UserStatus status = UserStatus.ACTIVE;

	@Column(name = "created_at")
	@CreationTimestamp
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	@UpdateTimestamp
	private LocalDateTime updatedAt;
}
