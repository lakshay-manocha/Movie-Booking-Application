package com.Microservice.Catalog.Theatre.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Microservice.Catalog.Theatre.entity.Theatre;
import com.Microservice.Catalog.Theatre.service.TheatreService;

@RestController
@RequestMapping("/theatre/v1")
public class TheatreController {

    private final TheatreService theatreService;

    public TheatreController(
            TheatreService theatreService) {

        this.theatreService = theatreService;
    }

    @PostMapping("/add")
    public ResponseEntity<Theatre> addTheatre(
            @RequestBody Theatre theatre) {

        return ResponseEntity.ok(
                theatreService.adddTheatre(theatre));
    }

    @GetMapping("/search/all")
    public ResponseEntity<List<Theatre>> retrieveTheatre() {

        return ResponseEntity.ok(
                theatreService.retrievealltheatre());
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<Optional<Theatre>> retrieveById(
            @PathVariable("id") String theatreId) {

        return ResponseEntity.ok(
                theatreService.retrievebyid(theatreId));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTheatreById(
            @PathVariable("id") String theatreId) {

        theatreService.deleteById(theatreId);

        return ResponseEntity.ok(
                "Theatre deleted successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Theatre> updateTheatreById(
            @PathVariable("id") String theatreId,
            @RequestBody Theatre updatedTheatre) {

        return ResponseEntity.ok(
                theatreService.updateTheatreById(
                        theatreId,
                        updatedTheatre));
    }

    @GetMapping("/active")
    public ResponseEntity<List<Theatre>> getActiveTheatre() {

        return ResponseEntity.ok(
                theatreService.getactivetheatre());
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<Theatre>> getTheatreByCity(
            @PathVariable String city) {

        return ResponseEntity.ok(
                theatreService.gettheatrebycity(city));
    }

    @GetMapping("/state/{state}")
    public ResponseEntity<List<Theatre>> getTheatreByState(
            @PathVariable String state) {

        return ResponseEntity.ok(
                theatreService.gettheatrebystate(state));
    }

    @GetMapping("/pincode/{pincode}")
    public ResponseEntity<List<Theatre>> getTheatreByPincode(
            @PathVariable Integer pincode) {

        return ResponseEntity.ok(
                theatreService.gettheatrebypincode(pincode));
    }

    @GetMapping("/name/{theatreName}")
    public ResponseEntity<List<Theatre>> getTheatreByName(
            @PathVariable String theatreName) {

        return ResponseEntity.ok(
                theatreService.gettheatrebyname(theatreName));
    }
}