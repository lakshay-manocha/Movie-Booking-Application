package com.Microservice.Catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class CatelogServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatelogServiceApplication.class, args);
	}

}