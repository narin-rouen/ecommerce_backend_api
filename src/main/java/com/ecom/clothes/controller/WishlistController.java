package com.ecom.clothes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.clothes.config.SecurityUser;
import com.ecom.clothes.dto.common.PageRequest;

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

}
