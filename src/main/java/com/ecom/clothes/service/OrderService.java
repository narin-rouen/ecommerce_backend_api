package com.ecom.clothes.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.clothes.repository.CartItemRepository;
import com.ecom.clothes.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

	private final OrderRepository orderRepository;
	private final CartItemRepository cartItemRepository;
}
