package com.microservice.paymentservice.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.microservice.paymentservice.entity.Payment;
import com.microservice.paymentservice.repository.PaymentRepository;

@Service
public class PaymentService {
	
	private final PaymentRepository paymentrepository;
	
	
	public PaymentService(PaymentRepository paymentrepository) {
		this.paymentrepository = paymentrepository;
	}
	
	public Payment createPayment(
            String bookingId,
            Long showId,
            Long seatId,
            String userId,
            Integer amount) {

		Payment payment = Payment.builder()
		        .bookingId(bookingId)
		        .showId(showId)
		        .seatId(seatId)
		        .userId(userId)
		        .amount(amount)
		        .currency("INR")
		        .paymentStatus(Payment.PaymentStatus.CREATED)
		        .createdAt(LocalDateTime.now())
		        .updatedAt(LocalDateTime.now())
		        .build();

        return paymentrepository.save(payment);
    }
	
	public Payment getPayment(String paymentId) {
		return paymentrepository.findById(paymentId)
				.orElseThrow(()->
				new RuntimeException("No such Payment found"));
	}

	public Payment getPaymentByBooking(String bookingId) {
		return paymentrepository.findById(bookingId)
				.orElseThrow(()->
				new RuntimeException("No such Payment found"));
	}


}
