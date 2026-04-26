package com.ecom.clothes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.clothes.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
