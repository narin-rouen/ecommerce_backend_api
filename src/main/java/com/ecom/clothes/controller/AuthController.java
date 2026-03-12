package com.ecom.clothes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.clothes.dto.request.LoginRequest;
import com.ecom.clothes.dto.request.RefreshTokenRequest;
import com.ecom.clothes.dto.request.RegisterRequest;
import com.ecom.clothes.dto.response.AuthResponse;
import com.ecom.clothes.dto.response.UserSummaryResponse;
import com.ecom.clothes.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/api/auth/register")
	public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
		AuthResponse response = authService.register(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PostMapping("/api/auth/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
		AuthResponse response = authService.login(request);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/api/auth/refresh")
	public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
		AuthResponse response = authService.refresh(request);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/api/auth/me")
	public ResponseEntity<UserSummaryResponse> getCurrentUser() {
		UserSummaryResponse response = authService.getCurrentUser();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/api/auth/health")
	public ResponseEntity<String> healthCheck() {
		return ResponseEntity.ok("Auth service is running!");
	}
}
