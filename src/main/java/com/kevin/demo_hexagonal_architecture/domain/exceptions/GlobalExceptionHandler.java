package com.kevin.demo_hexagonal_architecture.domain.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevin.demo_hexagonal_architecture.application.dto.GenericErrorDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@ControllerAdvice
@AllArgsConstructor
@Slf4j
public class GlobalExceptionHandler {
    private final ObjectMapper objectMapper;
    @ExceptionHandler({MovieAlreadyExistsException.class, MovieNotFoundException.class})
    public Mono<ResponseEntity<GenericErrorDto>> handleMovieAlreadyExistsException(MovieAlreadyExistsException ex, ServerWebExchange exchange) {
        HttpStatus status;
        try {
            status = ex.getHttpStatus();
        } catch (IllegalArgumentException e) {
            log.warn("Código de estado inválido en la excepción: {}, {}", ex.getHttpStatus(), ex.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        GenericErrorDto errorReponse = new GenericErrorDto(
                status.value(),
                ex.getMessage(),
                exchange.getRequest().getPath().value()
        );

        return Mono.just(new ResponseEntity<>(errorReponse,status));
    }
}
