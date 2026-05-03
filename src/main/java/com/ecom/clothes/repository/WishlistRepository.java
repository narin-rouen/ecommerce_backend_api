package com.ecom.clothes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.clothes.entity.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

	Page<Wishlist> findAllByUserId(Long userId, Pageable pageable);
}
