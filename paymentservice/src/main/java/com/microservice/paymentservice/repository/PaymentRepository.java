package com.microservice.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.paymentservice.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {

}
