package com.microservice.paymentservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.paymentservice.entity.Payment;
import com.microservice.paymentservice.service.PaymentService;

@RestController
@RequestMapping("payment/v1")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(
            PaymentService paymentService){

        this.paymentService=paymentService;
    }

    @GetMapping("/{paymentId}")
    public Payment getPayment(
            @PathVariable String paymentId){

        return paymentService.getPayment(paymentId);
    }

    @GetMapping("/booking/{bookingId}")
    public Payment getByBooking(
            @PathVariable String bookingId){

        return paymentService.getPaymentByBooking(
                bookingId);
    }

}
