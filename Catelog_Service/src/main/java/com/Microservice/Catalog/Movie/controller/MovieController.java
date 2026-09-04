package com.Microservice.Catalog.Movie.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Microservice.Catalog.Movie.entity.Movie;
import com.Microservice.Catalog.Movie.service.MovieService;

@RestController
@RequestMapping("/movie/v1")
public class MovieController {

    private final MovieService movieservice;

    public MovieController(MovieService movieservice) {
        this.movieservice = movieservice;
    }

    @PostMapping("/add/movie")
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        Movie savemovie = movieservice.addMovie(movie);
        return ResponseEntity.ok(savemovie);
    }

    @GetMapping("/search/movie")
    public List<Movie> getall() {
        return movieservice.getActiveMovies();
    }

    @GetMapping("id/{movieId}")
    public Optional<Movie> getmoviebyid(@PathVariable String movieId) {
        return movieservice.getmoviebyid(movieId);
    }

    @DeleteMapping("delete/id/{movieId}")
    public void DeleteMoviebyid(@PathVariable String movieId) {
        movieservice.deletemoviebyid(movieId);
    }

    @PutMapping("/update/id/{id}")
    public ResponseEntity<Movie> updateMovie(
            @PathVariable String id,
            @RequestBody Movie updatedMovie) {

        Movie movie = movieservice.updateMovie(id, updatedMovie);
        return ResponseEntity.ok(movie);
    }

    @GetMapping("/search/name/{movieName}")
    public List<Movie> getmoviebyname(@PathVariable String movieName) {
        return movieservice.getmoviebyname(movieName);
    }

    @GetMapping("/search/language/{movieLanguage}")
    public List<Movie> getmoviebylanguage(@PathVariable String movieLanguage) {
        return movieservice.getmoviebylanguage(movieLanguage);
    }

    @GetMapping("/search/genre/{movieGenre}")
    public List<Movie> getmoviebygenre(@PathVariable String movieGenre) {
        return movieservice.getmoviebygenre(movieGenre);
    }

    @GetMapping("/search/status/active")
    public List<Movie> getmovieisactive() {
        return movieservice.getmovieisactive();
    }

    @GetMapping("/search/movie/rating/{rating}")
    public List<Movie> getmoviebyrating(@PathVariable Double rating) {
        return movieservice.findByRatingGreaterThanEqual(rating);
    }

    @GetMapping("/search/movie/released")
    public List<Movie> getreleasedmovie() {
        return movieservice.findByReleaseDateLessThanEqual(LocalDate.now());
    }

    @GetMapping("/search/movie/upcomming")
    public List<Movie> getupcommingmovie() {
        return movieservice.findByReleaseDateGreaterThan(LocalDate.now());
    }
}