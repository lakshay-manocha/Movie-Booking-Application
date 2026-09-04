package com.Microservice.Catalog.Screen.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Microservice.Catalog.Screen.entity.Screen;
import com.Microservice.Catalog.Screen.service.ScreenService;

@RestController
@RequestMapping("/screen/v1")
public class ScreenController {

    private final ScreenService screenservice;

    public ScreenController(ScreenService screenservice) {
        this.screenservice = screenservice;
    }

    @PostMapping("/add")
    public ResponseEntity<Screen> addScreen(
            @RequestBody Screen screen) {

        return ResponseEntity.ok(
                screenservice.addScreen(screen));
    }

    @GetMapping("/search/all")
    public List<Screen> getScreens() {
        return screenservice.getScreens();
    }

    @GetMapping("/id/{id}")
    public Optional<Screen> getScreenById(
            @PathVariable("id") String screenId) {

        return screenservice.getScreenById(screenId);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteScreen(
            @PathVariable("id") String screenId) {

        screenservice.deleteScreen(screenId);

        return ResponseEntity.ok(
                "Screen deleted successfully");
    }

    @PutMapping("/update/{screenId}")
    public ResponseEntity<Screen> updateScreen(
            @PathVariable String screenId,
            @RequestBody Screen screen) {

        return ResponseEntity.ok(
                screenservice.updateScreenById(
                        screenId, screen));
    }

    @GetMapping("/theatreid/{theatreid}")
    public List<Screen> findByTheatreId(
            @PathVariable String theatreid) {

        return screenservice.findByTheatreId(theatreid);
    }

    @GetMapping("/active")
    public List<Screen> listActiveScreen() {
        return screenservice.findByIsActive();
    }

    @GetMapping("/type/{type}")
    public List<Screen> getScreenByType(
            @PathVariable("type") String screenType) {

        return screenservice.findByType(screenType);
    }
}