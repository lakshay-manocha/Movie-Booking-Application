package com.Microservice.Catalog.Show.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.Microservice.Catalog.Show.entity.Show;

@Repository
public interface ShowRepository extends MongoRepository<Show, String> {

    List<Show> findByTheatreId(String theatreId);

    List<Show> findByScreenId(String screenId);

    List<Show> findByActiveTrue();

    List<Show> findByMovieId(String movieId);

    List<Show> findByStartTimeGreaterThan(LocalDateTime startTime);

    List<Show> findByLanguageIgnoreCase(String language);

    List<Show> findByStartTimeBeforeAndEndTimeAfter(
            LocalDateTime currentTime,
            LocalDateTime currentTime2
    );
}