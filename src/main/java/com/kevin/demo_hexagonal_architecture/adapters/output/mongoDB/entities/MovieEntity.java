package com.kevin.demo_hexagonal_architecture.adapters.output.mongoDB.entities;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;

public record MovieEntity(
        @Id
        Long id,
        String title,
        String description,
        LocalDate releaseDate,
        String directorName
) {
}
