package com.kevin.demo_hexagonal_architecture.domain.usecases;

import com.kevin.demo_hexagonal_architecture.application.dto.MovieDto;
import com.kevin.demo_hexagonal_architecture.application.ports.MovieDao;
import com.kevin.demo_hexagonal_architecture.application.usecases.MetricsLogger;
import com.kevin.demo_hexagonal_architecture.application.usecases.MovieUseCase;
import com.kevin.demo_hexagonal_architecture.domain.Movie;
import com.kevin.demo_hexagonal_architecture.domain.exceptions.MovieAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Se encarga de inicializar los mocks automáticamente
public class MovieControllerIntegrationTest {
    @Mock
    private MovieDao movieDaoMock;

    @Mock
    private MetricsLogger metricsLogger; // Mock para evitar NullPointerException

    @InjectMocks
    private MovieUseCase useCase;
    @Test
    void testSaveMovie_whenMovieDoesNotExist_shouldSaveMovie() {
        var movieDto = new MovieDto("Titanic", "Desc", LocalDate.now(), "Director");

        when(movieDaoMock.findMovieByTitle(movieDto.title())).thenReturn(Mono.empty());
        when(movieDaoMock.saveMovie(any())).thenReturn(Mono.empty());

        StepVerifier.create(useCase.saveMovie(movieDto))
                .expectNext("Película guardada exitosamente")
                .verifyComplete();

        // Verificamos que no se haya llamado al logger de errores
        verify(metricsLogger, never()).logError(anyString(), any());
    }

    @Test
    void testSaveMovie_whenMovieExists_shouldReturnErrorAndLog() {
        var movieDto = new MovieDto("Titanic", "Desc", LocalDate.now(), "Director");

        // Simulamos que la película ya existe
        when(movieDaoMock.findMovieByTitle(movieDto.title()))
                .thenReturn(Mono.just(new Movie(1L, "Titanic", "Desc", LocalDate.now(), "Director")));

        StepVerifier.create(useCase.saveMovie(movieDto))
                .expectErrorMatches(throwable -> throwable instanceof MovieAlreadyExistsException &&
                        throwable.getMessage().equals("Movie already exists"))
                .verify();

        // Verificamos que el log de error haya sido llamado con el mensaje correcto
        verify(metricsLogger, times(1)).logError(eq("Intento de guardar película ya existente: Titanic"), isNull());
    }
}
