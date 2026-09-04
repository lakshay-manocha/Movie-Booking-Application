package com.microservice.common.events;

import java.time.LocalDateTime;

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
public class SeatBookingEvent {

    private Long showId;

    private Long screenId;

    private Long seatId;
    
    private String bookingId;

    private String seatNumber;

    private String seatType;

    private Integer price;

    private String userId;

    private String status;

    private Boolean active;

    private LocalDateTime bookingTime;
}