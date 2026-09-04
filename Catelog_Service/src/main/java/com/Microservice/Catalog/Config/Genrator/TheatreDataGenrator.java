package com.Microservice.Catalog.Config.Genrator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.Microservice.Catalog.Data.TheatreData;
import com.Microservice.Catalog.Theatre.entity.Theatre;
import com.Microservice.Catalog.Theatre.repository.TheatreRepository;

@Component
public class TheatreDataGenrator {

    private final TheatreData theatreData;
    private final TheatreRepository theatreRepository;

    // Manual Constructor
    public TheatreDataGenrator(
            TheatreData theatreData,
            TheatreRepository theatreRepository) {

        this.theatreData = theatreData;
        this.theatreRepository = theatreRepository;
    }

    public List<Theatre> generate() {

        System.out.println("Generating Theatre Data...");

        List<Theatre> theatres = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            Theatre theatre = new Theatre();

            theatre.setTheatreName(theatreData.theatreName());
            theatre.setCity(theatreData.city());
            theatre.setState(theatreData.state());
            theatre.setAddress(theatreData.address());
            theatre.setPincode(theatreData.pincode());
            theatre.setLatitude(theatreData.latitude());
            theatre.setLongitude(theatreData.longitude());
            theatre.setContactNumber(theatreData.contactNumber());
            theatre.setEmail(theatreData.email());

            theatre.setActive(true);
            theatre.setCreatedAt(theatreData.createdAt());
            theatre.setUpdatedAt(theatreData.updatedAt());

            theatres.add(theatre);
        }

        theatreRepository.saveAll(theatres);

        System.out.println("Theatres Generated: " + theatres.size());

        return theatres;
    }
}