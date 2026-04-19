package com.ecom.clothes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.clothes.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	void deleteByCartId(Long cartId);

	Optional<CartItem> findByCartIdAndProductSkuId(Long cartId, Long productSkuId);
}
