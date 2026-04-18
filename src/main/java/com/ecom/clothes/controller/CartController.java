package com.ecom.clothes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.clothes.config.SecurityUser;
import com.ecom.clothes.dto.common.PageRequest;
import com.ecom.clothes.dto.response.CartPageResponse;
import com.ecom.clothes.dto.response.CartResponse;
import com.ecom.clothes.service.CartItemService;
import com.ecom.clothes.service.CartService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CartController {

	private final CartService cartService;
	private final CartItemService cartItemService;

	@GetMapping("/api/admin/carts")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<CartPageResponse> getAllCarts(PageRequest request) {
		log.info("Getting all carts with page: {}, size: {}", request.page(), request.size());
		CartPageResponse response = cartService.GetAllCarts(request);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/api/user/mycart")
	@PreAuthorize("hasAnyRole('USER')")
	public ResponseEntity<CartResponse> getMyCart(@AuthenticationPrincipal SecurityUser securityUser) {
		log.info("Getting cart for user: {}", securityUser.getUsername());
		Long userId = securityUser.getUser().getId();

		CartResponse response = cartService.getCartByUserId(userId);

		return ResponseEntity.ok(response);
	}

	@PostMapping("/api/user/clearcart")
	@PreAuthorize("hasAnyRole('USER')")
	public ResponseEntity<Void> clearMyCart(@AuthenticationPrincipal SecurityUser securityUser) {
		log.info("Clearing cart for user: {}", securityUser.getUsername());
		Long userId = securityUser.getUser().getId();

		cartItemService.clearCartItemsByUserId(userId);

		return ResponseEntity.noContent().build();
	}
}
