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
public class SeatLockedEvent {

    private Long showId;

    private Long screenId;

    private Long seatId;

    private String seatNumber;

    private String userId;

    private Integer price;

    private String seatType;

    private LocalDateTime lockExpiresAt;
    
    private String bookingId;
    

}