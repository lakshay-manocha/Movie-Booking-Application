package com.microservice.seatbooking.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.seatbooking.entity.Seat;
import com.microservice.seatbooking.service.SeatLockingService;
import com.microservice.seatbooking.service.SeatService;



@RestController
@RequestMapping("/api/v1/seats")
public class SeatController {

    private final SeatService seatService;
    private final SeatLockingService seatLockingService;

    public SeatController(
            SeatService seatService,
            SeatLockingService seatLockingService) {

        this.seatService = seatService;
        this.seatLockingService = seatLockingService;
    }

    @PostMapping
    public ResponseEntity<Seat> addSeat(
            @RequestBody Seat seat) {

        return ResponseEntity.ok(
                seatService.addSeat(seat));
    }

    @GetMapping
    public ResponseEntity<List<Seat>> getAllSeats() {

        return ResponseEntity.ok(
                seatService.getAllSeat());
    }

    @GetMapping("/{seatId}")
    public ResponseEntity<List<Seat>> getSeatById(
            @PathVariable Long seatId) {

        return ResponseEntity.ok(
                seatService.findBySeatId(seatId));
    }

    @GetMapping("/show/{showId}")
    public ResponseEntity<List<Seat>> getSeatsByShowId(
            @PathVariable Long showId) {

        return ResponseEntity.ok(
                seatService.getSeatByShowId(showId));
    }

    @PostMapping("/{seatId}/lock/{userId}")
    public ResponseEntity<Seat> lockSeat(
            @PathVariable Long seatId,
            @PathVariable String userId) {

        return ResponseEntity.ok(
                seatLockingService.lockSeat(
                        seatId,
                        userId));
    }

    @PostMapping("/{seatId}/unlock/{userId}")
    public ResponseEntity<String> unlockSeat(
            @PathVariable Long seatId,
            @PathVariable String userId) {

        seatLockingService.unlockSeat(
                seatId,
                userId);

        return ResponseEntity.ok(
                "Seat unlocked successfully");
    }

    @PostMapping("/{seatId}/book/{userId}")
    public ResponseEntity<String> bookSeat(
            @PathVariable Long seatId,
            @PathVariable String userId) {

        seatLockingService.bookSeat(
                seatId,
                userId);

        return ResponseEntity.ok(
                "Seat booked successfully");
    }
}