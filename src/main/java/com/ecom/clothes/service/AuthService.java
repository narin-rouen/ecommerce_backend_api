package com.ecom.clothes.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecom.clothes.config.SecurityUser;
import com.ecom.clothes.dto.request.LoginRequest;
import com.ecom.clothes.dto.request.RefreshTokenRequest;
import com.ecom.clothes.dto.request.RegisterRequest;
import com.ecom.clothes.dto.response.AuthResponse;
import com.ecom.clothes.dto.response.UserSummaryResponse;
import com.ecom.clothes.entity.User;
import com.ecom.clothes.entity.UserStatus;
import com.ecom.clothes.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final JWTService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	@Transactional
	public AuthResponse register(RegisterRequest request) {
		// Check if user already exists
		if (userRepository.existsByEmail(request.email())) {
			throw new RuntimeException("User with this email already exists");
		}

		// Create new user with encoded password
		User user = User.builder().firstName(request.firstName()).lastName(request.lastName())
				.username(request.username()).email(request.email())
				.password(passwordEncoder.encode(request.password())).phoneNumber(request.phoneNumber())
				.role(request.role()).status(UserStatus.ACTIVE).build();

		User savedUser = userRepository.save(user);

		SecurityUser userDetails = new SecurityUser(savedUser);

		// Generate tokens
		String accessToken = jwtService.generateAccessToken(userDetails);
		String deviceId = "web-browser";
		String refreshToken = jwtService.generateRefreshToken(userDetails, deviceId);

		UserSummaryResponse summary = UserSummaryResponse.builder().id(savedUser.getId())
				.firstName(savedUser.getFirstName()).lastName(savedUser.getLastName()).email(savedUser.getEmail())
				.role(savedUser.getRole()).build();

		return AuthResponse.builder().accessToken(accessToken).refreshToken(refreshToken).tokenType("Bearer")
				.expiresIn("24 hours").refreshIn("7 days").user(summary).build();
	}

	public AuthResponse login(LoginRequest request) {
		// Authenticate user credential
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

		// Find user
		User user = userRepository.findByEmail(request.email())
				.orElseThrow(() -> new RuntimeException("User not found"));

		// Convert User to SecurityUser
		SecurityUser userDetails = new SecurityUser(user);

		// Generate tokens
		String accessToken = jwtService.generateAccessToken(userDetails);
		String deviceId = "web-browser";
		String refreshToken = jwtService.generateRefreshToken(userDetails, deviceId);

		UserSummaryResponse summary = UserSummaryResponse.builder().id(user.getId()).firstName(user.getFirstName())
				.lastName(user.getLastName()).email(user.getEmail()).role(user.getRole()).build();

		return AuthResponse.builder().accessToken(accessToken).refreshToken(refreshToken).tokenType("Bearer")
				.expiresIn("24 hours").refreshIn("7 days").user(summary).build();
	}

	public AuthResponse refresh(RefreshTokenRequest request) {
		// Get user email from refresh token
		String refreshToken = request.refreshToken();
		String email = jwtService.extractUsername(refreshToken, true)
				.orElseThrow(() -> new RuntimeException("Invalid refresh token"));

		// Find user
		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		// Convert User to SecurityUser
		SecurityUser userDetails = new SecurityUser(user);

		// Validate refresh token
		if (!jwtService.isTokenValid(refreshToken, userDetails, true)) {
			throw new RuntimeException("Invalid refresh token");
		}

		// Generate new access token, keep the same refresh token
		String accessToken = jwtService.generateAccessToken(userDetails);

		UserSummaryResponse summary = UserSummaryResponse.builder().id(user.getId()).firstName(user.getFirstName())
				.lastName(user.getLastName()).email(user.getEmail()).role(user.getRole()).build();

		return AuthResponse.builder().accessToken(accessToken).refreshToken(refreshToken).tokenType("Bearer")
				.expiresIn("24 hours").user(summary).build();
	}

	public UserSummaryResponse getCurrentUser() {
		// Extract authentication from SecurityContext
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			throw new RuntimeException("No authenticated user found");
		}

		String email = authentication.getName();

		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		return UserSummaryResponse.builder().id(user.getId()).firstName(user.getFirstName())
				.lastName(user.getLastName()).email(user.getEmail()).role(user.getRole()).build();
	}
}
