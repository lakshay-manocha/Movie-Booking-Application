package com.Microservice.Catalog.Screen.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;

@Document(collection = "screen_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Screen {

    @Id
    private String screenId;

    private String theatreId;

    private String movieId;

    private String screenName;

    private Integer totalSeats;

    private String screenType;   // IMAX, 3D, 4DX, Standard

    private boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}