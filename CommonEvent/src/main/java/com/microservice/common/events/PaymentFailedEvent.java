package com.microservice.common.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentFailedEvent {

    private String paymentId;
    
    private String bookingId;

    private Long seatId;

    private Long showId;

    private String userId;

    private String reason;
}