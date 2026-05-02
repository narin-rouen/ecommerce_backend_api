package com.ecom.clothes.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.clothes.dto.common.PageRequest;
import com.ecom.clothes.dto.request.CreatePaymentRequest;
import com.ecom.clothes.dto.response.OrderPageResponse;
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

import jakarta.validation.Valid;
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

	@Transactional(readOnly = true)
	public OrderPageResponse getAllOrders(@Valid PageRequest request) {
		log.info("Fetch all order records with page: {}, size: {}", request.page(), request.size());

		Page<Order> orderPage = orderRepository.findAll(request.toPageable());

		List<OrderResponse> orderResponses = orderPage.getContent().stream().map(OrderResponse::fromEntity).toList();

		return new OrderPageResponse(orderResponses, orderPage.getNumber(), orderPage.getSize(), request.sortBy(),
				request.direction(), request.search());
	}

	@Transactional(readOnly = true)
	public OrderResponse getOrderById(Long orderId) {
		log.info("Fetch order details with id: {}", orderId);

		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

		return OrderResponse.fromEntity(order);
	}

	@Transactional(readOnly = true)
	public OrderPageResponse getAllByUserId(Long userId, PageRequest request) {
		log.info("User with id: {} fetches all their order records", userId);

		Page<Order> orderPage = orderRepository.findAllByUserId(userId, request.toPageable());

		List<OrderResponse> orderResponses = orderPage.getContent().stream().map(OrderResponse::fromEntity).toList();

		return new OrderPageResponse(orderResponses, orderPage.getNumber(), orderPage.getSize(), request.sortBy(),
				request.direction(), request.search());
	}

	@Transactional(readOnly = true)
	public OrderResponse getOrderByIdAndUserId(Long orderId, Long userId) {
		log.info("User with id: {} fetch the order details with id: {}", userId, orderId);

		Order order = orderRepository.findByIdAndUserId(orderId, userId).orElseThrow(
				() -> new RuntimeException("Order not found with id: " + orderId + " for user id: " + userId));

		return OrderResponse.fromEntity(order);
	}

	@Transactional
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
		order.setPayment(payment);
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

	@Transactional
	public OrderResponse updateStatus(Long orderId, String status) {
		log.info("Update order status with order id: {}, with new status: {}", orderId, status);
		OrderStatus newStatus;
		try {
			newStatus = OrderStatus.valueOf(status.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid order status: " + status);
		}

		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new RuntimeException("Order not found with id : " + orderId));

		validateStatusTransition(order.getStatus(), newStatus);

		order.setStatus(newStatus);
		Order updatedOrder = orderRepository.save(order);
		log.info("Update order status successfully!");

		return OrderResponse.fromEntity(updatedOrder);
	}

	@Transactional
	public OrderResponse confirmReceive(Long userId, Long orderId) {
		log.info("Confirm Receive order with id: {}", orderId);

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new RuntimeException("Order not found with id : " + orderId));

		// validate if order really belong to user
		if (!order.getUser().getId().equals(userId)) {
			log.warn("User {} attempted to confirm receipt of order {} belonging to user {}", userId, orderId,
					order.getUser().getId());
			throw new AccessDeniedException("Order does not belong to this user");
		}

		// confirm receive
		OrderStatus newStatus = OrderStatus.RECEIVED;
		validateStatusTransition(order.getStatus(), newStatus);

		OrderStatus oldStatus = order.getStatus();
		order.setStatus(newStatus);
		Order updatedOrder = orderRepository.save(order);

		log.info("Order {} status updated from {} to {} by user {}", orderId, oldStatus, newStatus, userId);

		return OrderResponse.fromEntity(updatedOrder);
	}

	private void validateStatusTransition(OrderStatus currentStatus, OrderStatus newStatus) {
		// Cannot transition to the same status
		if (currentStatus == newStatus) {
			throw new IllegalStateException(String.format("Order is already in %s status", currentStatus));
		}

		// Terminal states - no further transitions allowed
		if (currentStatus == OrderStatus.CANCELLED || currentStatus == OrderStatus.RETURNED
				|| currentStatus == OrderStatus.REFUNDED) {
			throw new IllegalStateException(String
					.format("Cannot update status for orders in %s status. This is a terminal state.", currentStatus));
		}

		// Validate allowed transitions based on current status
		switch (currentStatus) {
		case SUBMITTED:
			if (newStatus != OrderStatus.PROCESSING && newStatus != OrderStatus.CANCELLED) {
				throw new IllegalStateException(
						String.format("Cannot transition from %s to %s. Allowed transitions: PROCESSING, CANCELLED",
								currentStatus, newStatus));
			}
			break;

		case PROCESSING:
			if (newStatus != OrderStatus.SHIPPED && newStatus != OrderStatus.CANCELLED) {
				throw new IllegalStateException(
						String.format("Cannot transition from %s to %s. Allowed transitions: SHIPPED, CANCELLED",
								currentStatus, newStatus));
			}
			break;

		case SHIPPED:
			if (newStatus != OrderStatus.DELIVERED && newStatus != OrderStatus.RECEIVED) {
				throw new IllegalStateException(
						String.format("Cannot transition from %s to %s. Allowed transitions: DELIVERED, RECEIVED",
								currentStatus, newStatus));
			}
			break;

		case DELIVERED:
			if (newStatus != OrderStatus.RECEIVED) {
				throw new IllegalStateException(String
						.format("Cannot transition from %s to %s. Only RECEIVED is allowed", currentStatus, newStatus));
			}
			break;

		case RECEIVED:
			if (newStatus != OrderStatus.RETURNED) {
				throw new IllegalStateException(String
						.format("Cannot transition from %s to %s. Only RETURNED is allowed", currentStatus, newStatus));
			}
			break;

		default:
			throw new IllegalStateException(String.format("Unknown current status: %s", currentStatus));
		}
	}

}
