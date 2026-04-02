package com.ecom.clothes.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.clothes.dto.common.PageRequest;
import com.ecom.clothes.dto.request.CreateAddressRequest;
import com.ecom.clothes.dto.request.UpdateAddressRequest;
import com.ecom.clothes.dto.response.AddressPageResponse;
import com.ecom.clothes.dto.response.AddressResponse;
import com.ecom.clothes.dto.response.UserAddressResponse;
import com.ecom.clothes.dto.response.UserSummaryResponse;
import com.ecom.clothes.entity.Address;
import com.ecom.clothes.entity.User;
import com.ecom.clothes.exception.UserNotFoundException;
import com.ecom.clothes.repository.AddressRepository;
import com.ecom.clothes.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AddressService {

	private final AddressRepository addressRepository;
	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public AddressPageResponse getAllAddress(PageRequest request) {
		log.info("Fetch all address with page: {}, size:{} ", request.page(), request.size());

		Page<Address> addressPage = addressRepository.findAllActive(request.toPageable());

		List<AddressResponse> addressResponse = addressPage.getContent().stream().map(AddressResponse::from).toList();

		return new AddressPageResponse(addressResponse, addressPage.getNumber(), addressPage.getSize(),
				request.sortBy(), request.direction(), request.search());

	}

	@Transactional(readOnly = true)
	public AddressPageResponse search(PageRequest request) {
		log.info("Search all address with keyword: {}", request.search());

		Page<Address> addressPage = addressRepository.searchActive(request.search(), request.toPageable());

		List<AddressResponse> addressResponse = addressPage.getContent().stream().map(AddressResponse::from).toList();

		return new AddressPageResponse(addressResponse, addressPage.getNumber(), addressPage.getSize(),
				request.sortBy(), request.direction(), request.search());

	}

	@Transactional(readOnly = true)
	public AddressResponse getAddressById(Long id) {
		log.info("Fetch address by id: {}", id);

		Address address = addressRepository.findActiveById(id)
				.orElseThrow(() -> new RuntimeException("Address not found with id: " + id));

		return AddressResponse.from(address);

	}

	@Transactional(readOnly = true)
	public AddressPageResponse getAllDeletedAddress(PageRequest request) {
		log.info("Fetch all deleted address with page: {}, size:{} ", request.page(), request.size());

		Page<Address> addressPage = addressRepository.findAllDeleted(request.toPageable());

		List<AddressResponse> addressResponse = addressPage.getContent().stream().map(AddressResponse::from).toList();

		return new AddressPageResponse(addressResponse, addressPage.getNumber(), addressPage.getSize(),
				request.sortBy(), request.direction(), request.search());

	}

	@Transactional(readOnly = true)
	public UserAddressResponse getAllAddressesByUserId(Long userId) {
		log.info("Fetch address by user id: {}", userId);

		// check if user exist
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

		List<Address> addresses = addressRepository.findActiveByUserId(userId);

		List<AddressResponse> addressResponse = addresses.stream().map(AddressResponse::from).toList();

		return new UserAddressResponse(UserSummaryResponse.from(user), addressResponse);

	}

	@Transactional(readOnly = true)
	public AddressResponse getAddressByIdForUser(Long userId, Long addressId) {
		log.info("Fetch address by id: {} for user id: {}", addressId, userId);

		// check if user exist
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

		if (user == null) {
			throw new UserNotFoundException("User not found with id: " + userId);

		}

		Address address = addressRepository.findActiveByIdAndUserId(addressId, userId).orElseThrow(
				() -> new RuntimeException("Address not found with id: " + addressId + " for user id: " + userId));

		return AddressResponse.from(address);

	}

	@Transactional
	public AddressResponse createAddress(Long userId, CreateAddressRequest request) {
		log.info("Create new address with user id: {}, title: {}", userId, request.title());

		// check if user exist
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

		Address address = new Address();
		address.setUser(user);
		address.setTitle(request.title());
		address.setLine1(request.line1());
		address.setLine2(request.line2());
		address.setCountry(request.country());
		address.setCity(request.city());
		address.setPostcode(request.postcode());

		Address savedAddress = addressRepository.save(address);
		log.info("Address created with id: {}", savedAddress.getId());

		return AddressResponse.from(savedAddress);

	}

	@Transactional
	public AddressResponse updateAddress(Long id, UpdateAddressRequest request, Long userId) {
		log.info("Update address with id: {}", id);

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

		if (user == null) {
			throw new UserNotFoundException("User not found with id: " + userId);
		}

		Address address = addressRepository.findActiveById(id)
				.orElseThrow(() -> new RuntimeException("Address not found with id: " + id));

		if (!address.getUser().getId().equals(userId)) {
			throw new RuntimeException("Address with id: " + id + " does not belong to user with id: " + userId);
		}

		if (request.title() != null) {
			address.setTitle(request.title());
		}
		if (request.line1() != null) {
			address.setLine1(request.line1());
		}
		if (request.line2() != null) {
			address.setLine2(request.line2());
		}
		if (request.country() != null) {
			address.setCountry(request.country());
		}
		if (request.city() != null) {
			address.setCity(request.city());
		}
		if (request.postcode() != null) {
			address.setPostcode(request.postcode());
		}

		Address updatedAddress = addressRepository.save(address);
		log.info("Address updated with id: {}", updatedAddress.getId());

		return AddressResponse.from(updatedAddress);

	}

	@Transactional
	public void deleteAddress(Long id) {
		log.info("Delete address with id: {}", id);

		Address address = addressRepository.findActiveById(id)
				.orElseThrow(() -> new RuntimeException("Address not found with id: " + id));

		address.setDeletedAt(java.time.LocalDateTime.now());
		addressRepository.save(address);

		log.info("Address soft deleted with id: {}", id);
	}

	@Transactional
	public void deleteAddressForUser(Long userId, Long addressId) {
		log.info("User with id: {} delete address with id: {}", userId, addressId);

		// check if user exist
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

		if (user == null) {
			throw new UserNotFoundException("User not found with id: " + userId);
		}

		Address address = addressRepository.findActiveByIdAndUserId(addressId, userId).orElseThrow(
				() -> new RuntimeException("Address not found with id: " + addressId + " for user id: " + userId));

		if (!address.getUser().getId().equals(userId)) {
			throw new RuntimeException("Address with id: " + addressId + " does not belong to user with id: " + userId);
		}

		address.setDeletedAt(java.time.LocalDateTime.now());
		addressRepository.save(address);

		log.info("Address soft deleted with id: {} for user id: {}", addressId, userId);

	}

}
