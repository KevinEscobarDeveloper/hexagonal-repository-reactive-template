package com.kevin.demo_hexagonal_architecture.application.dto;

import java.time.LocalDate;

public record MovieDto(
        String title,
        String description,
        LocalDate releaseDate,
        String directorName
) {
}
