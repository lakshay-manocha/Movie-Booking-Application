package com.Microservice.Catalog.Show.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Microservice.Catalog.Show.entity.Show;
import com.Microservice.Catalog.Show.service.ShowService;

@RestController
@RequestMapping("/show/v1")
public class ShowController {

    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    // Add Show
    @PostMapping("/add")
    public ResponseEntity<Show> addShow(
            @RequestBody Show show) {

        Show savedShow = showService.addShow(show);
        return ResponseEntity.ok(savedShow);
    }

    // Get All Shows
    @GetMapping("/all")
    public List<Show> getAllShows() {
        return showService.listShow();
    }

    // Get Show By ID
    @GetMapping("/id/{showId}")
    public Show getShowById(
            @PathVariable String showId) {

        return showService.findById(showId);
    }

    // Soft Delete Show
    @DeleteMapping("/delete/{showId}")
    public ResponseEntity<String> deleteShow(
            @PathVariable String showId) {

        showService.deleteShow(showId);

        return ResponseEntity.ok(
                "Show deleted successfully");
    }

    // Update Show
    @PutMapping("/update/{showId}")
    public ResponseEntity<Show> updateShow(
            @PathVariable String showId,
            @RequestBody Show show) {

        Show updatedShow =
                showService.updateShowById(showId, show);

        return ResponseEntity.ok(updatedShow);
    }

    // Find by Theatre ID
    @GetMapping("/theatre/{theatreId}")
    public List<Show> getShowsByTheatre(
            @PathVariable String theatreId) {

        return showService.findByTheatre(theatreId);
    }

    // Find by Screen ID
    @GetMapping("/screen/{screenId}")
    public List<Show> getShowsByScreen(
            @PathVariable String screenId) {

        return showService.findByScreen(screenId);
    }

    // Find Active Shows
    @GetMapping("/active")
    public List<Show> getActiveShows() {
        return showService.findByActiveTrue();
    }

    // Find by Movie ID
    @GetMapping("/movie/{movieId}")
    public List<Show> getShowsByMovie(
            @PathVariable String movieId) {

        return showService.findByMovieId(movieId);
    }

    // Find upcoming shows
    @GetMapping("/after")
    public List<Show> getShowsAfter(
            @RequestParam LocalDateTime startTime) {

        return showService
                .findByStartTimeGreaterThan(startTime);
    }

    // Find by language
    @GetMapping("/language/{language}")
    public List<Show> getShowsByLanguage(
            @PathVariable String language) {

        return showService.findByLanguage(language);
    }

    // Currently running shows
    @GetMapping("/running")
    public List<Show> getRunningShows() {

        return showService
                .findByStartTimeBeforeAndEndTimeAfter();
    }
}