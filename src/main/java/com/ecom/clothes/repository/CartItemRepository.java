package com.ecom.clothes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.clothes.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	void deleteByCartId(Long cartId);
}
