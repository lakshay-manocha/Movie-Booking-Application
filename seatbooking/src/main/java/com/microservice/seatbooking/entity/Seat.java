package com.microservice.seatbooking.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;
    
    private String bookingId;

    private Long showId;

    private Long screenId;

    // A1, A2, B5...
    private String seatNumber;

    private Integer rowNumber;

    private Integer columnNumber;

    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    public enum SeatStatus {
        AVAILABLE,
        LOCKED,
        BOOKED
    }
    
    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    // REGULAR, PREMIUM, RECLINER
    public enum SeatType{
    	REGULAR, 
    	PREMIUM, 
    	RECLINER
    }
    
    private Integer price;

    // Lock owner
    private String lockedBy;

    // Lock expiry timestamp
    private LocalDateTime lockedExpiresAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean active;

    @Version
    private Long version;
}