package com.Microservice.Catalog.Show.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;

@Document(collection = "show_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Show {

    @Id
    private String showId;

    private String movieId;

    private String theatreId;

    private String screenId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Double basePrice;

    private String language;

    private boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}