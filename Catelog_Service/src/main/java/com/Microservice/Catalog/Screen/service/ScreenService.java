package com.Microservice.Catalog.Screen.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.Microservice.Catalog.Movie.repository.MongoMovieRepository;
import com.Microservice.Catalog.Screen.entity.Screen;
import com.Microservice.Catalog.Screen.repository.ScreenRepository;
import com.Microservice.Catalog.Theatre.repository.TheatreRepository;

@Service
public class ScreenService {

    private final ScreenRepository screenRepository;
    private final TheatreRepository theatreRepository;
    private final MongoMovieRepository movieRepository;

    public ScreenService(
            ScreenRepository screenRepository,
            TheatreRepository theatreRepository,
            MongoMovieRepository movieRepository) {

        this.screenRepository = screenRepository;
        this.theatreRepository = theatreRepository;
        this.movieRepository = movieRepository;
    }

    @Caching(
            put = @CachePut(
                    value = "screens",
                    key = "#result.screenId"
            ),
            evict = {
                    @CacheEvict(value = "all_screens", allEntries = true),
                    @CacheEvict(value = "active_screens", allEntries = true),
                    @CacheEvict(value = "screens_by_theatre", allEntries = true),
                    @CacheEvict(value = "screens_by_movie", allEntries = true)
            }
    )
    public Screen addScreen(Screen screen) {

        theatreRepository.findById(screen.getTheatreId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Theatre not found: "
                                        + screen.getTheatreId()));

        screen.setActive(true);
        screen.setCreatedAt(LocalDateTime.now());
        screen.setUpdatedAt(LocalDateTime.now());

        return screenRepository.save(screen);
    }

    @Cacheable(
            value = "all_screens",
            key = "'all'"
    )
    public List<Screen> getScreens() {
        return screenRepository.findAll();
    }

    @Cacheable(
            value = "screens",
            key = "#screenId"
    )
    public Optional<Screen> getScreenById(String screenId) {
        return screenRepository.findById(screenId);
    }

    @Caching(evict = {
            @CacheEvict(value = "screens", key = "#screenId"),
            @CacheEvict(value = "all_screens", allEntries = true),
            @CacheEvict(value = "active_screens", allEntries = true),
            @CacheEvict(value = "screens_by_theatre", allEntries = true),
            @CacheEvict(value = "screens_by_movie", allEntries = true),
            @CacheEvict(value = "screens_by_type", allEntries = true)
    })
    public void deleteScreen(String screenId) {

        Screen existingScreen = screenRepository.findById(screenId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Screen not found: " + screenId));

        existingScreen.setActive(false);
        existingScreen.setUpdatedAt(LocalDateTime.now());

        screenRepository.save(existingScreen);
    }

    @Caching(
            put = @CachePut(
                    value = "screens",
                    key = "#screenId"
            ),
            evict = {
                    @CacheEvict(value = "all_screens", allEntries = true),
                    @CacheEvict(value = "active_screens", allEntries = true),
                    @CacheEvict(value = "screens_by_theatre", allEntries = true),
                    @CacheEvict(value = "screens_by_movie", allEntries = true),
                    @CacheEvict(value = "screens_by_type", allEntries = true)
            }
    )
    public Screen updateScreenById(
            String screenId,
            Screen updatedScreen) {

        Screen existingScreen = screenRepository.findById(screenId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Screen not found: " + screenId));

        existingScreen.setScreenName(updatedScreen.getScreenName());
        existingScreen.setTotalSeats(updatedScreen.getTotalSeats());
        existingScreen.setScreenType(updatedScreen.getScreenType());
        existingScreen.setTheatreId(updatedScreen.getTheatreId());
        existingScreen.setMovieId(updatedScreen.getMovieId());
        existingScreen.setActive(updatedScreen.isActive());
        existingScreen.setUpdatedAt(LocalDateTime.now());

        return screenRepository.save(existingScreen);
    }

    @Cacheable(
            value = "screens_by_theatre",
            key = "#theatreId"
    )
    public List<Screen> findByTheatreId(String theatreId) {
        return screenRepository.findByTheatreId(theatreId);
    }

    @Cacheable(
            value = "screens_by_movie",
            key = "#movieId"
    )
    public List<Screen> findByMovieId(String movieId) {
        return screenRepository.findByMovieId(movieId);
    }

    @Cacheable(
            value = "active_screens",
            key = "'all'"
    )
    public List<Screen> findByIsActive() {
        return screenRepository.findByActiveTrue();
    }

    @Cacheable(
            value = "screens_by_type",
            key = "#screenType"
    )
    public List<Screen> findByType(String screenType) {
        return screenRepository.findByScreenType(screenType);
    }
}