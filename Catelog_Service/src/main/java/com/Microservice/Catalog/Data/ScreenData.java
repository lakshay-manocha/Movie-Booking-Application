package com.Microservice.Catalog.Data;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
@Getter
@Setter
public class ScreenData {

    private static final Random RANDOM = new Random();

    private static final List<String> SCREEN_NAMES = List.of(
            "Screen 1",
            "Screen 2",
            "Screen 3",
            "Screen 4",
            "Screen 5",
            "IMAX",
            "PXL",
            "4DX",
            "MX4D",
            "Dolby Atmos"
    );

    private static final List<String> SCREEN_TYPES = List.of(
            "2D",
            "3D",
            "IMAX",
            "4DX",
            "Dolby Cinema"
    );

    private static final List<Integer> TOTAL_SEATS = List.of(
            80,
            100,
            120,
            150,
            180,
            200,
            250,
            300
    );

    public String screenName() {
        return randomFrom(SCREEN_NAMES);
    }

    public String screenType() {
        return randomFrom(SCREEN_TYPES);
    }

    public Integer totalSeats() {
        return randomFrom(TOTAL_SEATS);
    }

    public boolean active() {
        return true;
    }

    public LocalDateTime createdAt() {
        return LocalDateTime.now();
    }

    public LocalDateTime updatedAt() {
        return LocalDateTime.now();
    }

    private <T> T randomFrom(List<T> list) {
        return list.get(RANDOM.nextInt(list.size()));
    }
}