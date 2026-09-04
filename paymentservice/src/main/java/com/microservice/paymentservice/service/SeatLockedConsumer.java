package com.microservice.paymentservice.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.microservice.common.events.KafkaTopics;
import com.microservice.common.events.PaymentCreatedEvent;
import com.microservice.common.events.SeatLockedEvent;
import com.microservice.paymentservice.entity.Payment;

@Service
public class SeatLockedConsumer {

    private final PaymentService paymentService;

    private final PaymentProducer producer;
    
    private final PaymentProcessingService paymentprocessing;

    public SeatLockedConsumer(
            PaymentService paymentService,
            PaymentProducer producer,
            PaymentProcessingService paymentprocessing){

        this.paymentService=paymentService;
        this.producer=producer;
		this.paymentprocessing = paymentprocessing;
    }

    @KafkaListener(
            topics=KafkaTopics.SEAT_LOCKED,
            groupId="payment-service")
    public void consume(SeatLockedEvent event){

        System.out.println(
                "Seat Locked Event Received");

        Payment payment =
                paymentService.createPayment(

                        event.getBookingId(),
                        event.getShowId(),
                        event.getSeatId(),
                        event.getUserId(),
                        event.getPrice());

        PaymentCreatedEvent paymentEvent =
                PaymentCreatedEvent.builder()

                        .paymentId(payment.getPaymentId())
                        .bookingId(payment.getBookingId())
                        .seatId(payment.getSeatId())
                        .showId(payment.getShowId())
                        .amount(payment.getAmount())
                        .status(payment.getPaymentStatus().name())
                        .build();

        producer.publish(paymentEvent);
        paymentprocessing.processPayment(
                payment.getPaymentId()
        );

    }

}