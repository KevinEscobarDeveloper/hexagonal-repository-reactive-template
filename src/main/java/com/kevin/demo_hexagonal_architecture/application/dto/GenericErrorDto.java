package com.kevin.demo_hexagonal_architecture.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GenericErrorDto {
    private final Integer errorCode;
    private final String message;
    private final String path;
}
