package com.ecom.clothes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecom.clothes.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
