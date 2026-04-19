package com.ecom.clothes.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.clothes.dto.common.PageRequest;
import com.ecom.clothes.dto.request.CreateCartItemRequest;
import com.ecom.clothes.dto.response.CartItemPageResponse;
import com.ecom.clothes.dto.response.CartItemResponse;
import com.ecom.clothes.entity.Cart;
import com.ecom.clothes.entity.CartItem;
import com.ecom.clothes.entity.ProductSku;
import com.ecom.clothes.repository.CartItemRepository;
import com.ecom.clothes.repository.CartRepository;
import com.ecom.clothes.repository.ProductSkuRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartItemService {

	private final CartItemRepository cartItemRepository;
	private final CartRepository cartRepository;
	private final CartService cartService;
	private final ProductSkuRepository productSkuRepository;

	@Transactional(readOnly = true)
	public CartItemPageResponse getAllCartItems(PageRequest request) {
		Page<CartItem> page = cartItemRepository.findAll(request.toPageable());

		List<CartItemResponse> responses = page.getContent().stream().map(CartItemResponse::from)
				.collect(Collectors.toList());

		return new CartItemPageResponse(responses, page.getNumber(), page.getSize(), request.sortBy(),
				request.direction(), request.search());
	}

	@Transactional(readOnly = true)
	public CartItemResponse getAllCartItems() {
		log.info("Getting all cart items");

		CartItem cartItem = cartItemRepository.findAll().stream().findFirst()
				.orElseThrow(() -> new RuntimeException("No cart items found"));

		return CartItemResponse.from(cartItem);
	}

	@Transactional(readOnly = true)
	public CartItemResponse getCartItemById(Long id) {
		log.info("Getting cart item with id: {}", id);

		CartItem cartItem = cartItemRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Cart item not found with id: " + id));

		return CartItemResponse.from(cartItem);
	}

	@Transactional
	public CartItemResponse createCartItem(Long userId, CreateCartItemRequest request) {
		log.info("Creating cart item for product with id: {}", request.productSkuId());

		// find cart by user id
		Cart cart = cartRepository.findByUserId(userId).orElse(null);

		// create cart for user for the first time if cart is not exist
		if (cart == null) {
			cartService.createCart(userId);
			cart = cartRepository.findByUserId(userId)
					.orElseThrow(() -> new RuntimeException("Cart not found with id: " + userId));
		}

		// validate product and stock
		ProductSku productSku = productSkuRepository.findActiveById(request.productSkuId())
				.orElseThrow(() -> new RuntimeException("Product SKU not found with id: " + request.productSkuId()));

		if (productSku.getQuantity() <= 0) {
			throw new RuntimeException("Product with id: " + request.productSkuId() + " is out of stock");
		}

		// if the cart item already exists, update the quantity
		CartItem existingCartItem = cartItemRepository.findByCartIdAndProductSkuId(cart.getId(), productSku.getId())
				.orElse(null);
		if (existingCartItem != null) {
			existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
			CartItem updatedCartItem = cartItemRepository.save(existingCartItem);
			log.info("Cart item with id: {} already exists, updated quantity to: {}", updatedCartItem.getId(),
					updatedCartItem.getQuantity());
			return CartItemResponse.from(updatedCartItem);
		}

		CartItem cartItem = new CartItem();
		cartItem.setCart(cart);
		cartItem.setProductSku(productSku);
		cartItem.setUnitPrice(productSku.getPrice());

		CartItem savedCartItem = cartItemRepository.save(cartItem);
		log.info("Cart item created with id: {}", savedCartItem.getId());

		return CartItemResponse.from(savedCartItem);
	}

	@Transactional
	public CartItemResponse updateCartItemQuantity(Long id, Integer quantity) {
		log.info("Updating cart item with id: {} to quantity: {}", id, quantity);

		CartItem cartItem = cartItemRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Cart item not found with id: " + id));

		if (quantity <= 0) {
			throw new RuntimeException("Quantity must be greater than 0");
		}

		cartItem.setQuantity(quantity);
		CartItem updatedCartItem = cartItemRepository.save(cartItem);
		log.info("Cart item with id: {} updated to quantity: {}", id, quantity);

		return CartItemResponse.from(updatedCartItem);
	}

	@Transactional
	public void deleteCartItem(Long id) {
		log.info("Deleting cart item with id: {}", id);

		CartItem cartItem = cartItemRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Cart item not found with id: " + id));

		cartItemRepository.delete(cartItem);
		log.info("Cart item with id: {} deleted successfully", id);
	}

	@Transactional
	public void clearCartItemsByUserId(Long userId) {
		log.info("Clearing cart items for user with id: {}", userId);

		Cart cart = cartRepository.findByUserId(userId)
				.orElseThrow(() -> new RuntimeException("Cart not found for user: " + userId));

		cartItemRepository.deleteByCartId(cart.getId());
		log.info("Cart items cleared for user: {}", userId);
	}

}
