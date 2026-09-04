package com.Microservice.Catalog.Config.Genrator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.Microservice.Catalog.Data.ScreenData;
import com.Microservice.Catalog.Screen.entity.Screen;
import com.Microservice.Catalog.Screen.repository.ScreenRepository;
import com.Microservice.Catalog.Theatre.entity.Theatre;

@Component
public class ScreenDataGenrator {

    private final ScreenData screenData;
    private final ScreenRepository screenRepository;

    // Manual Constructor
    public ScreenDataGenrator(
            ScreenData screenData,
            ScreenRepository screenRepository) {

        this.screenData = screenData;
        this.screenRepository = screenRepository;
    }

    public List<Screen> generate(List<Theatre> theatres) {

        System.out.println("Generating Screen Data...");

        Random random = new Random();
        List<Screen> screens = new ArrayList<>();

        for (Theatre theatre : theatres) {

            // Each theatre gets 2–4 screens
            int screenCount = 2 + random.nextInt(3);

            for (int i = 1; i <= screenCount; i++) {

                Screen screen = new Screen();

                screen.setScreenName("Screen-" + i);
                screen.setScreenType(screenData.screenType());
                screen.setTotalSeats(screenData.totalSeats());

                // Business relationship:
                // One Theatre -> Many Screens
                screen.setTheatreId(theatre.getTheatreId());

                screen.setActive(true);
                screen.setCreatedAt(screenData.createdAt());
                screen.setUpdatedAt(screenData.updatedAt());

                screens.add(screen);
            }
        }

        screenRepository.saveAll(screens);

        System.out.println("Screens Generated: " + screens.size());

        return screens;
    }
}