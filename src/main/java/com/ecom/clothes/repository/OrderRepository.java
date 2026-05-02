package com.ecom.clothes.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.clothes.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	Page<Order> findAllByUserId(Long userId, Pageable pageable);

	Optional<Order> findByIdAndUserId(Long orderId, Long userId);

}
