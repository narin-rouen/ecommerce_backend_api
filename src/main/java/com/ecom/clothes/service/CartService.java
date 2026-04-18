package com.ecom.clothes.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.clothes.dto.common.PageRequest;
import com.ecom.clothes.dto.response.CartPageResponse;
import com.ecom.clothes.dto.response.CartResponse;
import com.ecom.clothes.entity.Cart;
import com.ecom.clothes.entity.User;
import com.ecom.clothes.repository.CartRepository;
import com.ecom.clothes.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

	private final CartRepository cartRepository;
	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public CartPageResponse GetAllCarts(PageRequest request) {
		Page<Cart> page = cartRepository.findAll(request.toPageable());

		List<CartResponse> responses = page.getContent().stream().map(CartResponse::from).collect(Collectors.toList());

		return new CartPageResponse(responses, page.getNumber(), page.getSize(), request.sortBy(), request.direction(),
				request.search());
	}

	@Transactional(readOnly = true)
	public CartResponse getCartByUserId(Long userId) {
		log.info("Getting cart with id: {}", userId);

		Cart cart = cartRepository.findByUserId(userId)
				.orElseThrow(() -> new RuntimeException("Cart not found with id: " + userId));

		return CartResponse.from(cart);
	}

	@Transactional
	public void createCart(Long userId) {
		log.info("Creating cart for user with id: {}", userId);

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

		Cart cart = new Cart();
		cart.setUser(user);

		Cart savedCart = cartRepository.save(cart);
		log.info("Cart created with id: {}", savedCart.getId());
	}

}
