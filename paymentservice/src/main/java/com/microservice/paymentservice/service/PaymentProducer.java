package com.microservice.paymentservice.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.microservice.common.events.KafkaTopics;
import com.microservice.common.events.PaymentCreatedEvent;
import com.microservice.common.events.PaymentFailedEvent;
import com.microservice.common.events.PaymentSucceededEvent;

@Service
public class PaymentProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PaymentProducer(
            KafkaTemplate<String, Object> kafkaTemplate){

        this.kafkaTemplate=kafkaTemplate;
    }

    public void publish(PaymentCreatedEvent event){

        kafkaTemplate.send(
                KafkaTopics.PAYMENT_CREATED,
                event.getPaymentId(),
                event);

        System.out.println(
                "Payment Created Event Published");
    }
    
    public void publishSucess(PaymentSucceededEvent event) {
    	kafkaTemplate.send(
    			KafkaTopics.PAYMENT_SUCCESS,
    			event.getPaymentId(),
    			event);
    }
    
    public void publishFaliure(PaymentFailedEvent event) {
    	kafkaTemplate.send(
    			KafkaTopics.PAYMENT_FAILED,
    			event.getPaymentId(),
    			event);
    }

}