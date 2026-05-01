package com.ecom.clothes.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.clothes.config.SecurityUser;
import com.ecom.clothes.dto.request.CreatePaymentRequest;
import com.ecom.clothes.dto.response.OrderResponse;
import com.ecom.clothes.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController {

	private final OrderService orderService;

	@PostMapping("/api/user/orders")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<OrderResponse> placeOrder(@AuthenticationPrincipal SecurityUser securityUser,
			@Valid @RequestBody CreatePaymentRequest paymentRequest) {
		Long userId = securityUser.getUser().getId();
		log.info("Received request to place order for userId: {}", userId);
		OrderResponse response = orderService.placeOrder(userId, paymentRequest);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/api/admin/order/{orderId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable Long orderId, 
			@RequestBody Map<String, String> body){
		if (!body.containsKey("status") || body.get("status").isEmpty()) {
	        return ResponseEntity.badRequest().build();
	    }
		String status = body.get("status");
		log.info("Admin update status order with id: {}", orderId);
		OrderResponse response = orderService.updateStatus(orderId, status);
		return ResponseEntity.ok(response);>
	}

	@PostMapping("/api/user/order/{orderId}")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<OrderResponse> confirmReceive(@AuthenticationPrincipal SecurityUser securityUser,
			@PathVariable Long orderId) {
		Long userId = securityUser.getUser().getId();
		log.info("User confirms revieve order with id: {}", orderId);
		OrderResponse response = orderService.confirmReceive(userId, orderId);
		return ResponseEntity.ok(response);
	}
}
