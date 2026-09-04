package com.Microservice.Catalog.Movie.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.Microservice.Catalog.Movie.entity.Movie;

public interface MongoMovieRepository extends MongoRepository <Movie, String> {
	
	List<Movie> findByMovieNameContainingIgnoreCase(String movieName);

	List<Movie> findByLanguageContainingIgnoreCase(String language);

	List<Movie> findByGenreContainingIgnoreCase(String movie_Genre);

	List<Movie> findByActiveTrue();

	List<Movie> findByRatingGreaterThanEqual(Double rating);

	List<Movie> findByReleaseDateLessThan(LocalDate now);

	List<Movie> findByReleaseDateGreaterThan(LocalDate now);

	List<Movie> findByReleaseDateLessThanEqual(LocalDate now);

}
