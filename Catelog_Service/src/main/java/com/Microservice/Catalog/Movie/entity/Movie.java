package com.Microservice.Catalog.Movie.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Data
@Document(collection="movie_table")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

	@Id
	private String movieId;

	private String movieName;

	private String language;

	private String certificate;

	private Integer duration;

	private String posterUrl;

	private Double rating;

	private LocalDate releaseDate;

	private String genre;

	private boolean active;
	
	private LocalDateTime startTime;

	private LocalDateTime endTime;

	private Double basePrice;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
	
}
