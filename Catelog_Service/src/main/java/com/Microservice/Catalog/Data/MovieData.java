package com.Microservice.Catalog.Data;


import com.github.javafaker.Faker;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
@Getter
@Setter
public class MovieData {

    private static final Faker faker = new Faker(new Locale("en-IN"));
    private static final Random random = new Random();

    public static final List<String> MOVIE_NAMES = List.of(
            "Avengers: Endgame",
            "Interstellar",
            "Inception",
            "The Dark Knight",
            "Dune: Part Two",
            "Oppenheimer",
            "Jawan",
            "Animal",
            "Pushpa 2",
            "KGF Chapter 2",
            "Leo",
            "Salaar",
            "Pathaan",
            "War",
            "Doctor Strange",
            "Spider-Man: No Way Home",
            "Iron Man",
            "The Batman",
            "John Wick 4",
            "Mission Impossible: Dead Reckoning",
            "RRR",
            "Kalki 2898 AD",
            "Sita Ramam",
            "Chhava",
            "The Conjuring"
    );

    public static final List<String> LANGUAGES = List.of(
            "Hindi",
            "English",
            "Tamil",
            "Telugu",
            "Kannada",
            "Malayalam"
    );

    public static final List<String> GENRES = List.of(
            "Action",
            "Drama",
            "Comedy",
            "Sci-Fi",
            "Adventure",
            "Thriller",
            "Horror",
            "Fantasy",
            "Crime",
            "Romance"
    );

    public static final List<String> CERTIFICATES = List.of(
            "U",
            "U/A",
            "A"
    );

    public static final List<String> POSTERS = List.of(
            "https://picsum.photos/300/450?random=1",
            "https://picsum.photos/300/450?random=2",
            "https://picsum.photos/300/450?random=3",
            "https://picsum.photos/300/450?random=4",
            "https://picsum.photos/300/450?random=5",
            "https://picsum.photos/300/450?random=6"
    );

    public String movieName() {
        return randomFrom(MOVIE_NAMES);
    }

    public String language() {
        return randomFrom(LANGUAGES);
    }

    public String genre() {
        return randomFrom(GENRES);
    }

    public String certificate() {
        return randomFrom(CERTIFICATES);
    }

    public Integer duration() {
        return ThreadLocalRandom.current().nextInt(90, 201);
    }

    public Double rating() {
        return Math.round(ThreadLocalRandom.current().nextDouble(6.0, 9.9) * 10) / 10.0;
    }

    public Double basePrice() {
        return (double) ThreadLocalRandom.current().nextInt(150, 601);
    }

    public String posterUrl() {
        return randomFrom(POSTERS);
    }

    public LocalDate releaseDate() {
        return LocalDate.now().minusDays(
                ThreadLocalRandom.current().nextInt(0, 1500)
        );
    }

    public LocalDateTime startTime() {
        return LocalDateTime.now()
                .plusDays(ThreadLocalRandom.current().nextInt(0, 7))
                .withHour(List.of(9,12,15,18,21).get(random.nextInt(5)))
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }

    public LocalDateTime endTime(LocalDateTime start, Integer duration) {
        return start.plusMinutes(duration);
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

    public String randomDirector() {
        return faker.name().fullName();
    }

    private <T> T randomFrom(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }
}