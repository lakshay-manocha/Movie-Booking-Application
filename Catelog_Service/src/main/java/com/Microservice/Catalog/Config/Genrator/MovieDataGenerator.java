package com.Microservice.Catalog.Config.Genrator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.Microservice.Catalog.Data.MovieData;
import com.Microservice.Catalog.Movie.entity.Movie;
import com.Microservice.Catalog.Movie.repository.MongoMovieRepository;

@Component
public class MovieDataGenerator {

    private final MovieData movieData;
    private final MongoMovieRepository movieRepository;

    // Manual constructor
    public MovieDataGenerator(
            MovieData movieData,
            MongoMovieRepository movieRepository) {

        this.movieData = movieData;
        this.movieRepository = movieRepository;
    }

    public List<Movie> generate() {

        System.out.println("Generating Movies...");

        List<Movie> movies = new ArrayList<>();

        for (int i = 0; i < 50; i++) {

            Movie movie = new Movie();

            movie.setMovieName(movieData.movieName());
            movie.setLanguage(movieData.language());
            movie.setGenre(movieData.genre());
            movie.setCertificate(movieData.certificate());
            movie.setDuration(movieData.duration());
            movie.setPosterUrl(movieData.posterUrl());
            movie.setRating(movieData.rating());
            movie.setReleaseDate(movieData.releaseDate());
            movie.setBasePrice(movieData.basePrice());

            movie.setStartTime(movieData.startTime());

            movie.setEndTime(
                    movieData.endTime(
                            movie.getStartTime(),
                            movie.getDuration()));

            movie.setActive(true);
            movie.setCreatedAt(movieData.createdAt());
            movie.setUpdatedAt(movieData.updatedAt());

            movies.add(movie);
        }

        movieRepository.saveAll(movies);

        System.out.println("Movies saved: " + movies.size());

        return movies;
    }
}