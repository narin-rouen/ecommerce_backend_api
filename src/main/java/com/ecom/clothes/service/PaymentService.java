package com.ecom.clothes.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.clothes.dto.request.CreatePaymentRequest;
import com.ecom.clothes.entity.Payment;
import com.ecom.clothes.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PaymentService {

	private final PaymentRepository paymentRepository;

	@Transactional
	public void createPayment(CreatePaymentRequest request) {
		log.info("Create payment with amount: {}", request.amount());

		Payment payment = new Payment();
		payment.setAmount(request.amount());
		payment.setProvider(request.provider());

		Payment savedPayment = paymentRepository.save(payment);
		log.info("Payment created with id: {}", savedPayment.getId());
	}
}
