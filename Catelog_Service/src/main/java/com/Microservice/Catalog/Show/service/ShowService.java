package com.Microservice.Catalog.Show.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import com.Microservice.Catalog.Movie.repository.MongoMovieRepository;
import com.Microservice.Catalog.Screen.entity.Screen;
import com.Microservice.Catalog.Screen.repository.ScreenRepository;
import com.Microservice.Catalog.Show.entity.Show;
import com.Microservice.Catalog.Show.repository.ShowRepository;
import com.Microservice.Catalog.Theatre.repository.TheatreRepository;
import com.Microservice.Catalog.kafka.producer.ShowEventProducer;
import com.microservice.common.events.ShowCreatedEvent;

@Service
public class ShowService {

    private final ShowRepository showRepository;
    private final TheatreRepository theatreRepository;
    private final MongoMovieRepository movieRepository;
    private final ScreenRepository screenRepository;
    private final ShowEventProducer showEventProducer;

    public ShowService(
            ShowRepository showRepository,
            TheatreRepository theatreRepository,
            MongoMovieRepository movieRepository,
            ScreenRepository screenRepository,
            ShowEventProducer showEventProducer) {

        this.showRepository = showRepository;
        this.theatreRepository = theatreRepository;
        this.movieRepository = movieRepository;
        this.screenRepository = screenRepository;
        this.showEventProducer = showEventProducer;
    }

    @Caching(
            put = @CachePut(
                    value = "show",
                    key = "#result.showId"
            ),
            evict = {
                    @CacheEvict(value = "all_show", allEntries = true),
                    @CacheEvict(value = "show_by_theatre_id", allEntries = true),
                    @CacheEvict(value = "show_by_screen_id", allEntries = true),
                    @CacheEvict(value = "show_by_active", allEntries = true),
                    @CacheEvict(value = "show_by_movie_id", allEntries = true),
                    @CacheEvict(value = "show_by_language", allEntries = true)
            }
    )
    public Show addShow(Show show) {

        theatreRepository.findById(show.getTheatreId())
                .orElseThrow(() ->
                        new RuntimeException("Theatre not found"));

        movieRepository.findById(show.getMovieId())
                .orElseThrow(() ->
                        new RuntimeException("Movie not found"));

        Screen existingScreen = screenRepository
                .findById(show.getScreenId())
                .orElseThrow(() ->
                        new RuntimeException("Screen not found"));

        if (!existingScreen.getTheatreId()
                .equals(show.getTheatreId())) {

            throw new RuntimeException(
                    "Screen does not belong to this theatre");
        }

        if (show.getStartTime().isAfter(show.getEndTime())) {
            throw new RuntimeException(
                    "Start time cannot be after end time");
        }

        show.setActive(true);
        show.setCreatedAt(LocalDateTime.now());
        show.setUpdatedAt(LocalDateTime.now());

        Show newshow = showRepository.save(show);

        ShowCreatedEvent event =
                ShowCreatedEvent.builder()
                        .showId(newshow.getShowId())
                        .screenId(newshow.getScreenId())
                        .totalSeat(existingScreen.getTotalSeats())
                        .basePrice(newshow.getBasePrice())
                        .screenType(existingScreen.getScreenType())
                        .build();

        showEventProducer.publishShowCreatedEvent(event);

        return newshow;
    }

    @Cacheable(value = "all_show", key = "'all'")
    public List<Show> listShow() {
        return showRepository.findAll();
    }

    @Caching(evict = {
            @CacheEvict(value = "show", key = "#showId"),
            @CacheEvict(value = "all_show", allEntries = true),
            @CacheEvict(value = "show_by_theatre_id", allEntries = true),
            @CacheEvict(value = "show_by_screen_id", allEntries = true),
            @CacheEvict(value = "show_by_active", allEntries = true),
            @CacheEvict(value = "show_by_movie_id", allEntries = true),
            @CacheEvict(value = "show_by_language", allEntries = true)
    })
    public void deleteShow(String showId) {

        Show existingShow = showRepository.findById(showId)
                .orElseThrow(() ->
                        new RuntimeException("Show not found"));

        existingShow.setActive(false);
        existingShow.setUpdatedAt(LocalDateTime.now());

        showRepository.save(existingShow);
    }

    @Cacheable(value = "show", key = "#showId")
    public Show findById(String showId) {
        return showRepository.findById(showId)
        		.orElseThrow(()-> new RuntimeException("Show does not found") );
    }

    @Cacheable(value = "show_by_theatre_id", key = "#theatreId")
    public List<Show> findByTheatre(String theatreId) {
        return showRepository.findByTheatreId(theatreId);
    }

    @Cacheable(value = "show_by_screen_id", key = "#screenId")
    public List<Show> findByScreen(String screenId) {
        return showRepository.findByScreenId(screenId);
    }

    @Cacheable(value = "show_by_active", key = "'active'")
    public List<Show> findByActiveTrue() {
        return showRepository.findByActiveTrue();
    }

    @Cacheable(value = "show_by_movie_id", key = "#movieId")
    public List<Show> findByMovieId(String movieId) {
        return showRepository.findByMovieId(movieId);
    }

    public List<Show> findByStartTimeGreaterThan(
            LocalDateTime startTime) {

        return showRepository
                .findByStartTimeGreaterThan(startTime);
    }

    @Cacheable(value = "show_by_language", key = "#language")
    public List<Show> findByLanguage(String language) {

        return showRepository
                .findByLanguageIgnoreCase(language);
    }

    public List<Show> findByStartTimeBeforeAndEndTimeAfter() {

        LocalDateTime now = LocalDateTime.now();

        return showRepository
                .findByStartTimeBeforeAndEndTimeAfter(now, now);
    }

    @Caching(
            put = @CachePut(
                    value = "show",
                    key = "#showId"
            ),
            evict = {
                    @CacheEvict(value = "all_show", allEntries = true),
                    @CacheEvict(value = "show_by_theatre_id", allEntries = true),
                    @CacheEvict(value = "show_by_screen_id", allEntries = true),
                    @CacheEvict(value = "show_by_active", allEntries = true),
                    @CacheEvict(value = "show_by_movie_id", allEntries = true),
                    @CacheEvict(value = "show_by_language", allEntries = true)
            }
    )
    public Show updateShowById(
            String showId,
            Show updatedShow) {

        Show existingShow = showRepository.findById(showId)
                .orElseThrow(() ->
                        new RuntimeException("Show not found"));

        movieRepository.findById(updatedShow.getMovieId())
                .orElseThrow(() ->
                        new RuntimeException("Movie not found"));

        theatreRepository.findById(updatedShow.getTheatreId())
                .orElseThrow(() ->
                        new RuntimeException("Theatre not found"));

        Screen screen = screenRepository
                .findById(updatedShow.getScreenId())
                .orElseThrow(() ->
                        new RuntimeException("Screen not found"));

        if (!screen.getTheatreId()
                .equals(updatedShow.getTheatreId())) {

            throw new RuntimeException(
                    "Screen does not belong to this theatre");
        }

        existingShow.setMovieId(updatedShow.getMovieId());
        existingShow.setTheatreId(updatedShow.getTheatreId());
        existingShow.setScreenId(updatedShow.getScreenId());
        existingShow.setStartTime(updatedShow.getStartTime());
        existingShow.setEndTime(updatedShow.getEndTime());
        existingShow.setBasePrice(updatedShow.getBasePrice());
        existingShow.setLanguage(updatedShow.getLanguage());
        existingShow.setActive(updatedShow.isActive());
        existingShow.setUpdatedAt(LocalDateTime.now());

        return showRepository.save(existingShow);
    }
}