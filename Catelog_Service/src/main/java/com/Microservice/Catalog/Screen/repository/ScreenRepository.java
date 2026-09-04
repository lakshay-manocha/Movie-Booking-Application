package com.Microservice.Catalog.Screen.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.Microservice.Catalog.Screen.entity.Screen;

@Repository
public interface ScreenRepository extends MongoRepository<Screen, String> {

    List<Screen> findByTheatreId(String theatreId);

    List<Screen> findByMovieId(String movieId);

    List<Screen> findByActiveTrue();

    List<Screen> findByScreenType(String screenType);
}