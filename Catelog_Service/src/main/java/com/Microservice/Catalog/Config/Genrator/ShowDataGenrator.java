package com.Microservice.Catalog.Config.Genrator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.Microservice.Catalog.Data.ShowData;
import com.Microservice.Catalog.Movie.entity.Movie;
import com.Microservice.Catalog.Screen.entity.Screen;
import com.Microservice.Catalog.Show.entity.Show;
import com.Microservice.Catalog.Show.repository.ShowRepository;

@Component
public class ShowDataGenrator {

    private final ShowData showData;
    private final ShowRepository showRepository;

    // Manual Constructor
    public ShowDataGenrator(
            ShowData showData,
            ShowRepository showRepository) {

        this.showData = showData;
        this.showRepository = showRepository;
    }

    public void generate(
            List<Movie> movies,
            List<Screen> screens) {

        System.out.println("Generating Show Data...");

        Random random = new Random();
        List<Show> shows = new ArrayList<>();

        for (Screen screen : screens) {

            // Each screen gets 2–4 shows
            int showCount = 2 + random.nextInt(3);

            for (int i = 0; i < showCount; i++) {

                Movie movie =
                        movies.get(random.nextInt(movies.size()));

                Show show = new Show();

                // ==========================
                // BUSINESS RELATIONSHIPS
                // ==========================

                // One Movie -> Many Shows
                show.setMovieId(movie.getMovieId());

                // One Screen -> Many Shows
                show.setScreenId(screen.getScreenId());

                // Denormalized for faster queries
                show.setTheatreId(screen.getTheatreId());

                // ==========================
                // SHOW DETAILS
                // ==========================

                show.setLanguage(movie.getLanguage());
                show.setBasePrice(movie.getBasePrice());

                show.setStartTime(showData.startTime());

                show.setEndTime(
                        showData.endTime(
                                show.getStartTime(),
                                movie.getDuration()));

                show.setActive(true);

                show.setCreatedAt(showData.createdAt());
                show.setUpdatedAt(showData.updatedAt());

                shows.add(show);
            }
        }

        showRepository.saveAll(shows);

        System.out.println("Shows Generated: " + shows.size());
    }
}