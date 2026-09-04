package com.microservice.seatbooking.kafka.producer;


import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.microservice.common.events.KafkaTopics;
import com.microservice.common.events.SeatBookingEvent;
import com.microservice.common.events.SeatLockedEvent;
import com.microservice.common.events.SeatUnlockedEvent;


@Service
public class SeatBookingProducer {


    private final KafkaTemplate<String, Object> kafkaTemplate;


    public SeatBookingProducer(
            KafkaTemplate<String, Object> kafkaTemplate) {

        this.kafkaTemplate = kafkaTemplate;
    }



    // Event 1:
    // Seat locked -> Payment service consumes this

    public void publishSeatLockedEvent(
            SeatLockedEvent event) {


        kafkaTemplate.send(
                KafkaTopics.SEAT_LOCKED,
                String.valueOf(event.getSeatId()),
                event
        );


        System.out.println(
                "SeatLockedEvent published for Seat : "
                + event.getSeatId()
        );
    }
    
    public void publishSeatUnlockedEvent(
    		SeatUnlockedEvent event) {
    	kafkaTemplate.send(
    			KafkaTopics.SEAT_UNLOCK,
    			String.valueOf(event.getSeatId()),
    			event
    			);
    }
    
    // Event 2:
    // Seat booked event

    public void publishSeatBookedEvent(
            SeatBookingEvent event) {


        kafkaTemplate.send(
                KafkaTopics.BOOKING_CREATED,
                String.valueOf(event.getSeatId()),
                event
        );


        System.out.println(
                "SeatBookingEvent published for Seat : "
                + event.getSeatId()
        );
    }

}