package com.Microservice.Catalog.Movie.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.Microservice.Catalog.Movie.entity.Movie;
import com.Microservice.Catalog.Movie.repository.MongoMovieRepository;



@Service
public class MovieService {

    private final MongoMovieRepository movieRepository;
    
    public MovieService (MongoMovieRepository movieRepository) {
    	this.movieRepository=movieRepository;
    }

    @CachePut(value = "movies", key = "#result.movieId")
    @CacheEvict(value = {
            "allMovies",
            "activeMovies",
            "releasedMovies",
            "upcomingMovies"
    }, allEntries = true)
    public Movie addMovie(Movie movie) {

        movie.setCreatedAt(LocalDateTime.now());
        movie.setUpdatedAt(LocalDateTime.now());

        return movieRepository.save(movie);
    }

    @Cacheable("allMovies")
    public List<Movie> getmovies() {
        return movieRepository.findAll();
    }

    @Cacheable("activeMovies")
    public List<Movie> getActiveMovies() {
        return movieRepository.findByActiveTrue();
    }

    @Cacheable(value = "movies", key = "#movieId")
    public Optional<Movie> getmoviebyid(String movieId) {
        return movieRepository.findById(movieId);
    }

    @Cacheable(value = "movieByName", key = "#movieName")
    public List<Movie> getmoviebyname(String movieName) {
        return movieRepository.findByMovieNameContainingIgnoreCase(movieName);
    }

    @Cacheable(value = "movieByLanguage", key = "#language")
    public List<Movie> getmoviebylanguage(String language) {
        return movieRepository.findByLanguageContainingIgnoreCase(language);
    }

    @Cacheable(value = "movieByGenre", key = "#genre")
    public List<Movie> getmoviebygenre(String genre) {
        return movieRepository.findByGenreContainingIgnoreCase(genre);
    }

    @Cacheable("activeMovies")
    public List<Movie> getmovieisactive() {
        return movieRepository.findByActiveTrue();
    }

    @Cacheable(value = "movieByRating", key = "#rating")
    public List<Movie> findByRatingGreaterThanEqual(Double rating) {
        return movieRepository.findByRatingGreaterThanEqual(rating);
    }

    @Cacheable("releasedMovies")
    public List<Movie> findByReleaseDateLessThanEqual(LocalDate date) {
        return movieRepository.findByReleaseDateLessThanEqual(date);
    }

    @Cacheable("upcomingMovies")
    public List<Movie> findByReleaseDateGreaterThan(LocalDate date) {
        return movieRepository.findByReleaseDateGreaterThan(date);
    }

    @CachePut(value = "movies", key = "#movieId")
    @CacheEvict(value = {
            "allMovies",
            "activeMovies",
            "releasedMovies",
            "upcomingMovies"
    }, allEntries = true)
    public Movie updateMovie(String movieId, Movie updatedMovie) {

        Movie existingMovie = movieRepository.findById(movieId)
                .orElseThrow(() ->
                        new RuntimeException("Movie not found with id: " + movieId));

        existingMovie.setMovieName(updatedMovie.getMovieName());
        existingMovie.setLanguage(updatedMovie.getLanguage());
        existingMovie.setCertificate(updatedMovie.getCertificate());
        existingMovie.setDuration(updatedMovie.getDuration());
        existingMovie.setPosterUrl(updatedMovie.getPosterUrl());
        existingMovie.setRating(updatedMovie.getRating());
        existingMovie.setReleaseDate(updatedMovie.getReleaseDate());
        existingMovie.setGenre(updatedMovie.getGenre());
        existingMovie.setActive(updatedMovie.isActive());
        existingMovie.setStartTime(updatedMovie.getStartTime());
        existingMovie.setEndTime(updatedMovie.getEndTime());
        existingMovie.setBasePrice(updatedMovie.getBasePrice());

        existingMovie.setUpdatedAt(LocalDateTime.now());

        return movieRepository.save(existingMovie);
    }

    @CacheEvict(value = {
            "movies",
            "allMovies",
            "activeMovies",
            "releasedMovies",
            "upcomingMovies"
    }, key = "#movieId", allEntries = true)
    public void deletemoviebyid(String movieId) {

        if (!movieRepository.existsById(movieId)) {
            throw new RuntimeException("Movie not found with id: " + movieId);
        }

        movieRepository.deleteById(movieId);
    }
}