package com.kevin.demo_hexagonal_architecture.domain.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class MovieAlreadyExistsException extends RuntimeException {
    private final HttpStatus httpStatus;
    public MovieAlreadyExistsException( String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
