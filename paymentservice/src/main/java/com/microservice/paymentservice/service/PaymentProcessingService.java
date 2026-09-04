package com.microservice.paymentservice.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.microservice.common.events.PaymentFailedEvent;
import com.microservice.common.events.PaymentSucceededEvent;
import com.microservice.paymentservice.entity.Payment;
import com.microservice.paymentservice.repository.PaymentRepository;

import jakarta.transaction.Transactional;

@Service
public class PaymentProcessingService {


	private final PaymentRepository repository;

	private final PaymentProducer producer;


	public PaymentProcessingService(
			PaymentRepository repository,
			PaymentProducer producer){

		this.repository=repository;
		this.producer=producer;

	}


	@Transactional
	public void processPayment(String paymentId){


		Payment payment =repository.findById(paymentId)
				.orElseThrow();


		try{


			payment.setPaymentStatus(
					Payment.PaymentStatus.PENDING
					);


			repository.save(payment);



			// Simulating gateway call

			boolean success=true;



			if(success){

				payment.setPaymentStatus(
						Payment.PaymentStatus.SUCCESS
						);


				payment.setTransactionId(
						UUID.randomUUID().toString()
						);


				repository.save(payment);



				PaymentSucceededEvent event =
						PaymentSucceededEvent.builder()

						.paymentId(payment.getPaymentId())
						.bookingId(payment.getBookingId())
						.seatId(payment.getSeatId())
						.showId(payment.getShowId())
						.userId(payment.getUserId())
						.amount(payment.getAmount())
						.transactionId(payment.getTransactionId())

						.build();


				producer.publishSucess(event);;



			}



		}catch(Exception e){


			payment.setPaymentStatus(
					Payment.PaymentStatus.FAILED
					);


			repository.save(payment);



			PaymentFailedEvent event =
					PaymentFailedEvent.builder()

					.paymentId(paymentId)

					.bookingId(payment.getBookingId())

					.seatId(payment.getSeatId())

					.showId(payment.getShowId())

					.reason(e.getMessage())

					.build();


			producer.publishFaliure(event);


		}


	}

}