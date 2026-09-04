package com.microservice.common.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShowCreatedEvent {
	private String showId;
	
	private String screenId;
	
	private Integer totalSeat;
	
	private Double basePrice;
	
	private String screenType;
	
	private String seatType;

    private Integer price;
    
    private Integer bookingId;
	
	
}
