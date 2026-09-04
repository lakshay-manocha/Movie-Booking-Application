package com.Microservice.Catalog.Data;


import com.github.javafaker.Faker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
@Getter
@Setter
public class TheatreData {

    private static final Faker faker = new Faker(new Locale("en-IN"));
    private static final Random RANDOM = new Random();

    private static final List<String> THEATRE_NAMES = List.of(
            "PVR Cinemas",
            "INOX",
            "Cinepolis",
            "Miraj Cinemas",
            "MovieTime",
            "Wave Cinemas",
            "Carnival Cinemas",
            "Mukta A2 Cinemas",
            "CineHub",
            "Movietime Deluxe"
    );

    private static final List<String> CITIES = List.of(
            "Delhi",
            "Noida",
            "Gurugram",
            "Faridabad",
            "Ghaziabad",
            "Mumbai",
            "Pune",
            "Bengaluru",
            "Hyderabad",
            "Chennai",
            "Kolkata",
            "Jaipur",
            "Lucknow",
            "Ahmedabad"
    );

    private static final List<String> STATES = List.of(
            "Delhi",
            "Uttar Pradesh",
            "Haryana",
            "Maharashtra",
            "Karnataka",
            "Tamil Nadu",
            "Telangana",
            "West Bengal",
            "Rajasthan",
            "Gujarat"
    );

    public String theatreName() {
        return randomFrom(THEATRE_NAMES) + " " + faker.number().numberBetween(1, 20);
    }

    public String city() {
        return randomFrom(CITIES);
    }

    public String state() {
        return randomFrom(STATES);
    }

    public String address() {
        return faker.address().streetAddress();
    }

    public Integer pincode() {
        return faker.number().numberBetween(110001, 999999);
    }

    public Double latitude() {
        return ThreadLocalRandom.current().nextDouble(8.0, 37.0);
    }

    public Double longitude() {
        return ThreadLocalRandom.current().nextDouble(68.0, 97.0);
    }

    public String contactNumber() {
        return "+91 " + faker.number().digits(10);
    }

    public String email() {
        return faker.internet().emailAddress();
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