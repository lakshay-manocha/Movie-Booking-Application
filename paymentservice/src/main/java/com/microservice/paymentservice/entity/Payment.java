package com.microservice.paymentservice.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    public enum PaymentStatus {


        CREATED,

        PENDING,

        SUCCESS,

        FAILED,

        CANCELLED,

        REFUNDED
    }
    public enum PaymentMethod {

        CARD,

        UPI,

        NETBANKING,

        WALLET
    }

	    @Id
	    @GeneratedValue(strategy = GenerationType.UUID)
	    private String paymentId;

	    private String bookingId;

	    private Long showId;

	    private Long seatId;

	    private String userId;

	    private Integer amount;

	    private String currency;

	    private String transactionId;

	    private String gatewayPaymentId;

	    private String gatewayOrderId;

	    @Enumerated(EnumType.STRING)
	    private PaymentStatus paymentStatus;

	    @Enumerated(EnumType.STRING)
	    private PaymentMethod paymentMethod;

	    private LocalDateTime createdAt;

	    private LocalDateTime updatedAt;
	    
	}