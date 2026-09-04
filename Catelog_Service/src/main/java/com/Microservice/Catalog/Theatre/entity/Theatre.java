package com.Microservice.Catalog.Theatre.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "theatre_table")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Theatre {

    @Id
    private String theatreId;

    private String theatreName;

    private String city;

    private String state;

    private String address;

    private Integer pincode;

    private Double latitude;

    private Double longitude;

    private String contactNumber;

    private String email;

    private boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}