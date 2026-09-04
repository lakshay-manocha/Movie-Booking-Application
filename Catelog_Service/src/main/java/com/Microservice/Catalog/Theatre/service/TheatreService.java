package com.Microservice.Catalog.Theatre.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import com.Microservice.Catalog.Theatre.entity.Theatre;
import com.Microservice.Catalog.Theatre.repository.TheatreRepository;

@Service
public class TheatreService {

    private final TheatreRepository theatreRepository;

    public TheatreService(TheatreRepository theatreRepository) {
        this.theatreRepository = theatreRepository;
    }

    public Theatre adddTheatre(Theatre theatre) {

        theatre.setActive(true);
        theatre.setCreatedAt(LocalDateTime.now());
        theatre.setUpdatedAt(LocalDateTime.now());

        return theatreRepository.save(theatre);
    }

    @Cacheable(value = "all_theatre", key = "'all'")
    public List<Theatre> retrievealltheatre() {
        return theatreRepository.findAll();
    }

    @Cacheable(value = "theatre", key = "#theatreId")
    public Optional<Theatre> retrievebyid(String theatreId) {
        return theatreRepository.findById(theatreId);
    }

    @Caching(evict = {
            @CacheEvict(value = "theatre", key = "#theatreId"),
            @CacheEvict(value = "all_theatre", allEntries = true),
            @CacheEvict(value = "active_theatre", allEntries = true),
            @CacheEvict(value = "theatre_by_city", allEntries = true),
            @CacheEvict(value = "theatre_by_state", allEntries = true),
            @CacheEvict(value = "theatre_by_pincode", allEntries = true),
            @CacheEvict(value = "theatre_by_name", allEntries = true)
    })
    public void deleteById(String theatreId) {

        Theatre existingTheatre =
                theatreRepository.findById(theatreId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Theatre not found"));

        existingTheatre.setActive(false);
        existingTheatre.setUpdatedAt(LocalDateTime.now());

        theatreRepository.save(existingTheatre);
    }

    @Caching(
            put = @CachePut(
                    value = "theatre",
                    key = "#theatreId"
            ),
            evict = {
                    @CacheEvict(value = "all_theatre", allEntries = true),
                    @CacheEvict(value = "active_theatre", allEntries = true),
                    @CacheEvict(value = "theatre_by_city", allEntries = true),
                    @CacheEvict(value = "theatre_by_state", allEntries = true),
                    @CacheEvict(value = "theatre_by_pincode", allEntries = true),
                    @CacheEvict(value = "theatre_by_name", allEntries = true)
            }
    )
    public Theatre updateTheatreById(
            String theatreId,
            Theatre updatedTheatre) {

        Theatre existingTheatre =
                theatreRepository.findById(theatreId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Theatre not found"));

        existingTheatre.setTheatreName(
                updatedTheatre.getTheatreName());

        existingTheatre.setCity(
                updatedTheatre.getCity());

        existingTheatre.setState(
                updatedTheatre.getState());

        existingTheatre.setAddress(
                updatedTheatre.getAddress());

        existingTheatre.setPincode(
                updatedTheatre.getPincode());

        existingTheatre.setLatitude(
                updatedTheatre.getLatitude());

        existingTheatre.setLongitude(
                updatedTheatre.getLongitude());

        existingTheatre.setContactNumber(
                updatedTheatre.getContactNumber());

        existingTheatre.setEmail(
                updatedTheatre.getEmail());

        existingTheatre.setActive(
                updatedTheatre.isActive());

        existingTheatre.setUpdatedAt(
                LocalDateTime.now());

        return theatreRepository.save(existingTheatre);
    }

    @Cacheable(value = "active_theatre", key = "'all'")
    public List<Theatre> getactivetheatre() {
        return theatreRepository.findByActiveTrue();
    }

    @Cacheable(value = "theatre_by_city", key = "#city")
    public List<Theatre> gettheatrebycity(String city) {
        return theatreRepository.findByCityIgnoreCase(city);
    }

    @Cacheable(value = "theatre_by_state", key = "#state")
    public List<Theatre> gettheatrebystate(String state) {
        return theatreRepository.findByStateIgnoreCase(state);
    }

    @Cacheable(value = "theatre_by_pincode", key = "#pincode")
    public List<Theatre> gettheatrebypincode(Integer pincode) {
        return theatreRepository.findByPincode(pincode);
    }

    @Cacheable(value = "theatre_by_name", key = "#theatreName")
    public List<Theatre> gettheatrebyname(String theatreName) {
        return theatreRepository
                .findByTheatreNameContainingIgnoreCase(theatreName);
    }
}