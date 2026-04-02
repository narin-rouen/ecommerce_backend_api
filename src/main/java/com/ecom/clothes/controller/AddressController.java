package com.ecom.clothes.controller;

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
import com.ecom.clothes.dto.request.CreateAddressRequest;
import com.ecom.clothes.dto.request.UpdateAddressRequest;
import com.ecom.clothes.dto.response.AddressPageResponse;
import com.ecom.clothes.dto.response.AddressResponse;
import com.ecom.clothes.dto.response.UserAddressResponse;
import com.ecom.clothes.service.AddressService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AddressController {

	private final AddressService addressService;

	@GetMapping("/api/admin/addresses")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<AddressPageResponse> getAllAdressesForAdmin(@Valid PageRequest request) {
		log.info("Get all addresses for admin with page: {}, size: {}", request.page(), request.size());
		AddressPageResponse response = addressService.getAllAddress(request);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/api/admin/addresses/search")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<AddressPageResponse> searchAdressesForAdmin(@Valid PageRequest request) {
		log.info("Search addresses for admin with keyword: {}", request.search());
		AddressPageResponse response = addressService.search(request);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/api/admin/addresses/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<AddressResponse> getAddressByIdForAdmin(@PathVariable Long id) {
		log.info("Get address by id for admin: {}", id);
		AddressResponse response = addressService.getAddressById(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/api/admin/adresses/deleted")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<AddressPageResponse> getDeletedAdressesForAdmin(@Valid PageRequest request) {
		log.info("Get deleted addresses for admin with page: {}, size: {}", request.page(), request.size());
		AddressPageResponse response = addressService.getAllDeletedAddress(request);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/api/admin/addresses/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Void> deleteAddressByIdForAdmin(@PathVariable Long id) {
		log.info("Delete address by id for admin: {}", id);
		addressService.deleteAddress(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/api/user/addresses")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<UserAddressResponse> GetAllAdressesForUser(
			@AuthenticationPrincipal SecurityUser currentUser) {
		log.info("Get all addresses for user id: {}", currentUser.getUser().getId());
		Long userId = currentUser.getUser().getId();
		UserAddressResponse response = addressService.getAllAddressesByUserId(userId);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/api/user/addresses/{id}")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<AddressResponse> getAddressByIdForUser(@AuthenticationPrincipal SecurityUser currentUser,
			@PathVariable Long id) {
		log.info("Get address by id for user id: {}, address id: {}", currentUser.getUser().getId(), id);
		Long userId = currentUser.getUser().getId();
		AddressResponse response = addressService.getAddressByIdForUser(id, userId);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/api/user/addresses")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<AddressResponse> createAddress(@AuthenticationPrincipal SecurityUser currentUser,
			@Valid @RequestBody CreateAddressRequest request) {
		log.info("Create address for user id: {}, request: {}", currentUser.getUser().getId(), request);
		Long userId = currentUser.getUser().getId();
		AddressResponse response = addressService.createAddress(userId, request);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/api/user/addresses/{id}")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<AddressResponse> updateAddress(@AuthenticationPrincipal SecurityUser currentUser,
			@PathVariable Long id, @Valid @RequestBody UpdateAddressRequest request) {
		log.info("Update address for user id: {}, address id: {}, request: {}", currentUser.getUser().getId(), id,
				request);
		Long userId = currentUser.getUser().getId();
		AddressResponse response = addressService.updateAddress(id, request, userId);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/api/user/addresses/{id}")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<Void> deleteAddress(@AuthenticationPrincipal SecurityUser currentUser,
			@PathVariable Long id) {
		log.info("Delete address for user id: {}, address id: {}", currentUser.getUser().getId(), id);
		Long userId = currentUser.getUser().getId();
		addressService.deleteAddressForUser(id, userId);
		return ResponseEntity.noContent().build();
	}
}
