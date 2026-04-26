package com.ecom.clothes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.clothes.config.SecurityUser;
import com.ecom.clothes.dto.response.OrderResponse;
import com.ecom.clothes.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController {

	private final OrderService orderService;

	@PostMapping("/api/user/orders")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<OrderResponse> placeOrder(@AuthenticationPrincipal SecurityUser securityUser) {
		Long userId = securityUser.getUser().getId();
		log.info("Received request to place order for userId: {}", userId);
		OrderResponse response = orderService.placeOrder(userId);
		return ResponseEntity.ok(response);
	}
}
