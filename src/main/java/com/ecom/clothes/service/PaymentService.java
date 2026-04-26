package com.ecom.clothes.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecom.clothes.dto.request.CreatePaymentRequest;
import com.ecom.clothes.entity.Payment;
import com.ecom.clothes.entity.PaymentStatus;
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
	public Payment createPayment(CreatePaymentRequest request) {
		log.info("Create payment with amount: {}", request.amount());

		// Validate payment details
		if (request.amount() == null || request.amount().compareTo(BigDecimal.ZERO) <= 0) {
			throw new RuntimeException("Invalid payment amount");
		}

		if (request.provider() == null || request.provider().isBlank()) {
			throw new RuntimeException("Payment provider is required");
		}

		Payment payment = new Payment();
		payment.setAmount(request.amount());
		payment.setProvider(request.provider());
		payment.setStatus(PaymentStatus.COMPLETED);
		payment.setTransactionId(generateTransactionId());
		payment.setPaymentMethod(request.paymentMethod());

		Payment savedPayment = paymentRepository.save(payment);
		log.info("Payment created with id: {}", savedPayment.getId());

		return savedPayment;
	}

	private String generateTransactionId() {
		return "TXN_" + System.currentTimeMillis() + "_" + (int) (Math.random() * 10000);
	}
}
