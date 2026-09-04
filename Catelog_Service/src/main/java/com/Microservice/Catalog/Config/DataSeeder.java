package com.Microservice.Catalog.Config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.Microservice.Catalog.Config.Genrator.MovieDataGenerator;
import com.Microservice.Catalog.Config.Genrator.ScreenDataGenrator;
import com.Microservice.Catalog.Config.Genrator.ShowDataGenrator;
import com.Microservice.Catalog.Config.Genrator.TheatreDataGenrator;
import com.Microservice.Catalog.Movie.entity.Movie;
import com.Microservice.Catalog.Movie.repository.MongoMovieRepository;
import com.Microservice.Catalog.Screen.entity.Screen;
import com.Microservice.Catalog.Screen.repository.ScreenRepository;
import com.Microservice.Catalog.Show.repository.ShowRepository;
import com.Microservice.Catalog.Theatre.entity.Theatre;
import com.Microservice.Catalog.Theatre.repository.TheatreRepository;

@Component
public class DataSeeder implements CommandLineRunner {

    private final MovieDataGenerator movieGenerator;
    private final TheatreDataGenrator theatreGenerator;
    private final ScreenDataGenrator screenGenerator;
    private final ShowDataGenrator showGenerator;

    private final MongoMovieRepository movieRepository;
    private final TheatreRepository theatreRepository;
    private final ScreenRepository screenRepository;
    private final ShowRepository showRepository;

    // Manual constructor
    public DataSeeder(
            MovieDataGenerator movieGenerator,
            TheatreDataGenrator theatreGenerator,
            ScreenDataGenrator screenGenerator,
            ShowDataGenrator showGenerator,
            MongoMovieRepository movieRepository,
            TheatreRepository theatreRepository,
            ScreenRepository screenRepository,
            ShowRepository showRepository) {

        this.movieGenerator = movieGenerator;
        this.theatreGenerator = theatreGenerator;
        this.screenGenerator = screenGenerator;
        this.showGenerator = showGenerator;

        this.movieRepository = movieRepository;
        this.theatreRepository = theatreRepository;
        this.screenRepository = screenRepository;
        this.showRepository = showRepository;
    }

    @Override
    public void run(String... args) {

        System.out.println("======================================");
        System.out.println("Cleaning old data...");
        System.out.println("======================================");

        // Delete in reverse dependency order
        showRepository.deleteAll();
        screenRepository.deleteAll();
        theatreRepository.deleteAll();
        movieRepository.deleteAll();

        System.out.println("Old data deleted successfully.");

        System.out.println("======================================");
        System.out.println("Generating fresh business data...");
        System.out.println("======================================");

        // 1. Generate movies
        List<Movie> movies = movieGenerator.generate();

        // 2. Generate theatres
        List<Theatre> theatres = theatreGenerator.generate();

        // 3. Generate screens linked to theatres
        List<Screen> screens = screenGenerator.generate(theatres);

        // 4. Generate shows linked to movies and screens
        showGenerator.generate(movies, screens);

        System.out.println("======================================");
        System.out.println("DataSeeder completed successfully!");
        System.out.println("Business hierarchy established:");
        System.out.println("Movie -> Show");
        System.out.println("Theatre -> Screen -> Show");
        System.out.println("======================================");
    }
}