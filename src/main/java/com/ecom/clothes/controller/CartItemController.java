package com.ecom.clothes.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.clothes.config.SecurityUser;
import com.ecom.clothes.dto.common.PageRequest;
import com.ecom.clothes.dto.request.CreateCartItemRequest;
import com.ecom.clothes.dto.response.CartItemPageResponse;
import com.ecom.clothes.dto.response.CartItemResponse;
import com.ecom.clothes.service.CartItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CartItemController {

	private final CartItemService cartItemService;

	@GetMapping("/api/user/cartItems")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<CartItemPageResponse> getAllCartItems(PageRequest request) {
		log.info("Getting all cart items with page: {}, size: {}", request.page(), request.size());
		CartItemPageResponse response = cartItemService.getAllCartItems(request);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/api/user/cart/add")
	@PreAuthorize("hasAnyRole('USER')")
	public ResponseEntity<CartItemResponse> addCartItem(@RequestBody CreateCartItemRequest request,
			@AuthenticationPrincipal SecurityUser securityUser) {
		log.info("Adding cart item for user: {}", securityUser.getUsername());
		Long userId = securityUser.getUser().getId();

		CartItemResponse response = cartItemService.createCartItem(userId, request);

		return ResponseEntity.ok(response);
	}

	@PutMapping("/api/user/cartItem/{id}")
	@PreAuthorize("hasAnyRole('USER')")
	public ResponseEntity<CartItemResponse> updateCartItemQuantity(@PathVariable Long id,
			@RequestBody Map<String, Integer> body) {
		return ResponseEntity.ok(cartItemService.updateCartItemQuantity(id, body.get("quantity")));
	}

	@GetMapping("/api/user/cartItem/{id}")
	@PreAuthorize("hasAnyRole('USER')")
	public ResponseEntity<CartItemResponse> getCartItemById(@PathVariable Long id) {
		log.info("Getting cart item with id: {}", id);

		CartItemResponse response = cartItemService.getCartItemById(id);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/api/user/cartItem/{id}")
	@PreAuthorize("hasAnyRole('USER')")
	public ResponseEntity<Void> deleteCartItemById(@PathVariable Long id) {
		log.info("Deleting cart item with id: {}", id);

		cartItemService.deleteCartItem(id);

		return ResponseEntity.noContent().build();
	}
}
