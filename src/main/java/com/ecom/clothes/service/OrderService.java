package com.ecom.clothes.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.clothes.dto.request.CreatePaymentRequest;
import com.ecom.clothes.dto.response.OrderResponse;
import com.ecom.clothes.entity.Cart;
import com.ecom.clothes.entity.CartItem;
import com.ecom.clothes.entity.Order;
import com.ecom.clothes.entity.OrderItem;
import com.ecom.clothes.entity.OrderStatus;
import com.ecom.clothes.entity.Payment;
import com.ecom.clothes.entity.User;
import com.ecom.clothes.repository.CartItemRepository;
import com.ecom.clothes.repository.OrderRepository;
import com.ecom.clothes.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

	private final OrderRepository orderRepository;
	private final CartItemRepository cartItemRepository;
	private final UserRepository userRepository;
	private final PaymentService paymentService;

	public OrderResponse placeOrder(Long userId, CreatePaymentRequest paymentRequest) {
		log.info("Placing order for userId: {}", userId);

		// Validate user existence
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

		// Validate cart items for the user
		List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
		if (cartItems.isEmpty()) {
			throw new RuntimeException("No items in cart for user id: " + userId);
		}

		// Check stock availability for each product in the cart
		for (CartItem item : cartItems) {
			if (item.getProductSku().getQuantity() < item.getQuantity()) {
				throw new RuntimeException(
						"Insufficient stock for product: " + item.getProductSku().getProduct().getName());
			}
		}

		// Calculate total amount for the order
		BigDecimal total = cartItems.stream()
				.map(item -> item.getProductSku().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		// Verify payment amount matches order total
		if (paymentRequest.amount().compareTo(total) != 0) {
			throw new RuntimeException("Payment amount does not match order total");
		}

		// Create payment first
		Payment payment = paymentService.createPayment(paymentRequest);

		// Create and save the order
		Order order = new Order();
		order.setUser(user);
		order.setTotal(total);
		order.setStatus(OrderStatus.SUBMITTED);

		for (CartItem cartItem : cartItems) {
			OrderItem orderItem = new OrderItem();
			orderItem.setOrder(order);
			orderItem.setProductSku(cartItem.getProductSku());
			orderItem.setQuantity(cartItem.getQuantity());
			order.getItems().add(orderItem);
		}

		Order savedOrder = orderRepository.save(order);
		log.info("Order placed successfully with id: {}", savedOrder.getId());

		// Update product stock quantities
		for (CartItem cartItem : cartItems) {
			var productSku = cartItem.getProductSku();
			productSku.setQuantity(productSku.getQuantity() - cartItem.getQuantity());
			// You might want to save this - productSkuRepository.save(productSku);
		}

		// Clear the user's cart after placing the order
		Cart userCart = cartItems.get(0).getCart();
		cartItemRepository.deleteByCartId(userCart.getId());

		return OrderResponse.fromEntity(savedOrder);

	}
}
