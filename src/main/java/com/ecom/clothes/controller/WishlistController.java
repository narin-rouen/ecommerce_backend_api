package com.ecom.clothes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.clothes.config.SecurityUser;
import com.ecom.clothes.dto.common.PageRequest;
import com.ecom.clothes.dto.request.CreateWishlistRequest;
import com.ecom.clothes.dto.response.WishlistPageResponse;
import com.ecom.clothes.dto.response.WishlistResponse;
import com.ecom.clothes.service.WishlistService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WishlistController {

	private final WishlistService wishlistService;

	@GetMapping("/api/user/wishlists")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<WishlistPageResponse> getAllWishlistByUserId(
			@AuthenticationPrincipal SecurityUser currentUser, @Valid PageRequest pageRequest) {
		Long userId = currentUser.getUser().getId();
		log.info("User with Id: {} fetches all their wishlist with page: {}, size: {}", userId, pageRequest.page(),
				pageRequest.size());
		WishlistPageResponse response = wishlistService.getAllWishlistByUserId(userId, pageRequest);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/api/user/wishlists")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<WishlistResponse> addToWishlist(@AuthenticationPrincipal SecurityUser currentUser,
			@Valid @RequestBody CreateWishlistRequest request) {
		Long userId = currentUser.getUser().getId();
		log.info("User with id: {} add prodcut sku id: {} to wishlist", userId, request.productSkuId());
		WishlistResponse response = wishlistService.addToWishlist(userId, request);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/api/user/wishlists/{wishlistId}")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<Void> removeWishlist(@AuthenticationPrincipal SecurityUser currentUser,
			@PathVariable Long wishlistId) {
		Long userId = currentUser.getUser().getId();
		log.info("User with id: {} remove wishlist with id: {}", userId, wishlistId);
		wishlistService.removeWishlist(userId, wishlistId);
		return ResponseEntity.noContent().build();
	}
}
