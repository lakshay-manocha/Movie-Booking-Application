package com.Microservice.Catalog.Theatre.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.Microservice.Catalog.Theatre.entity.Theatre;

@Repository
public interface TheatreRepository
        extends MongoRepository<Theatre, String> {

    List<Theatre> findByActiveTrue();

    List<Theatre> findByCityIgnoreCase(String city);

    List<Theatre> findByStateIgnoreCase(String state);

    List<Theatre> findByPincode(Integer pincode);

    List<Theatre> findByTheatreNameContainingIgnoreCase(
            String theatreName);
}