package com.ecom.clothes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecom.clothes.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	void deleteByCartId(Long cartId);

	Optional<CartItem> findByCartIdAndProductSkuId(Long cartId, Long productSkuId);

	@Query("SELECT ci FROM CartItem ci WHERE ci.cart.user.id = :userId")
	List<CartItem> findByUserId(@Param("userId") Long userId);

	@Modifying
	@Query("DELETE FROM CartItem ci WHERE ci.cart.user.id = :userId")
	void deleteByUserId(@Param("userId") Long userId);
}
