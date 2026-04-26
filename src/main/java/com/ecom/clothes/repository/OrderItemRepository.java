package com.ecom.clothes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.clothes.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
