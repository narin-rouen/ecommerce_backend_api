package com.ecom.clothes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecom.clothes.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

	@Query("SELECT a FROM Address a WHERE a.deletedAt IS NULL")
	Page<Address> findAllActive(Pageable pageable);

	@Query("SELECT a FROM Address a WHERE a.id = :id AND a.deletedAt IS NULL")
	Optional<Address> findActiveById(@Param("id") Long id);

	@Query("SELECT a FROM Address a WHERE a.deletedAt IS NOT NULL")
	Page<Address> findAllDeleted(Pageable pageable);

	@Query("SELECT a FROM Address a WHERE a.user.id = :userId AND a.deletedAt IS NULL")
	List<Address> findActiveByUserId(@Param("userId") Long userId);

	@Query("SELECT a FROM Address a WHERE a.id = :addressId AND a.user.id = :userId AND a.deletedAt IS NULL")
	Optional<Address> findActiveByIdAndUserId(@Param("addressId") Long addressId, @Param("userId") Long userId);

	@Query("SELECT a FROM Address a WHERE a.deletedAt IS NULL AND "
			+ "(LOWER(a.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR "
			+ "LOWER(a.line1) LIKE LOWER(CONCAT('%', :keyword, '%')) OR "
			+ "LOWER(a.country) LIKE LOWER(CONCAT('%', :keyword, '%')) OR "
			+ "LOWER(a.city) LIKE LOWER(CONCAT('%', :keyword, '%')) OR "
			+ "LOWER(a.postcode) LIKE LOWER(CONCAT('%', :keyword, '%')))")
	Page<Address> searchActive(@Param("keyword") String keyword, Pageable pageable);
}
