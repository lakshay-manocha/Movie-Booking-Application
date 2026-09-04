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
public class ShowData {

    private static final Random RANDOM = new Random();

    // Common multiplex show timings
    private static final List<Integer> SHOW_HOURS = List.of(
            9, 12, 15, 18, 21
    );

    private static final List<String> LANGUAGES = List.of(
            "Hindi",
            "English",
            "Tamil",
            "Telugu",
            "Kannada",
            "Malayalam"
    );

    public LocalDateTime startTime() {

        LocalDateTime date = LocalDateTime.now()
                .plusDays(ThreadLocalRandom.current().nextInt(0, 7));

        int hour = SHOW_HOURS.get(RANDOM.nextInt(SHOW_HOURS.size()));

        return date.withHour(hour)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }

    public LocalDateTime endTime(LocalDateTime startTime, Integer duration) {

        return startTime.plusMinutes(duration);
    }

    public Double basePrice() {

        return (double) ThreadLocalRandom.current().nextInt(150, 601);
    }

    public String language() {

        return LANGUAGES.get(RANDOM.nextInt(LANGUAGES.size()));
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
}