package com.microservice.seatbooking.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.microservice.common.events.KafkaTopics;
import com.microservice.common.events.ShowCreatedEvent;
import com.microservice.seatbooking.entity.Seat;
import com.microservice.seatbooking.entity.Seat.SeatType;
import com.microservice.seatbooking.repository.SeatRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShowEventConsumer {

    private final SeatRepository seatRepository;

    public ShowEventConsumer(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @KafkaListener(topics = KafkaTopics.SHOW_CREATED)
    public void consumeShowCreatedEvent(ShowCreatedEvent event) {

        System.out.println("Event received for Show: " + event.getShowId());

        List<Seat> seats = generateSeats(event);

        seatRepository.saveAll(seats);

        System.out.println("Seats created: " + seats.size());
    }

    private List<Seat> generateSeats(ShowCreatedEvent event) {

        List<Seat> seats = new ArrayList<>();

        int totalSeats = event.getTotalSeat();   // already Integer

        Long showId = Long.valueOf(event.getShowId());     // keep only if DB uses Long
        Long screenId = Long.valueOf(event.getScreenId());

        int seatsPerRow = 10;

        for (int i = 1; i <= totalSeats; i++) {

            Seat seat = new Seat();

            seat.setShowId(showId);
            seat.setScreenId(screenId);

            seat.setSeatNumber("S" + i);

            seat.setRowNumber((i - 1) / seatsPerRow);
            seat.setColumnNumber((i - 1) % seatsPerRow);

            // Double → Integer properly
            seat.setPrice(event.getBasePrice().intValue());

            seat.setSeatType(SeatType.REGULAR);

            seat.setActive(true);

            seat.setLockedBy(null);
            seat.setLockedExpiresAt(null);

            seat.setCreatedAt(LocalDateTime.now());
            seat.setUpdatedAt(LocalDateTime.now());

            seats.add(seat);
        }

        return seats;
    }
}