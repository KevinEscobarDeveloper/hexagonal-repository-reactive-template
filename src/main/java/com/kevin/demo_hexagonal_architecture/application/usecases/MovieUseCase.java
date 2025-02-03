package com.kevin.demo_hexagonal_architecture.application.usecases;

import com.kevin.demo_hexagonal_architecture.application.ports.MovieDao;
import com.kevin.demo_hexagonal_architecture.application.dto.MovieDto;
import com.kevin.demo_hexagonal_architecture.domain.Movie;
import com.kevin.demo_hexagonal_architecture.domain.exceptions.MovieAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieUseCase {
    private final MovieDao movieDao;

    public Mono<String> saveMovie(MovieDto movieDto) {
        //check if movie is already in db
        return movieDao.findMovieByTitle(movieDto.title())
                .hasElement()
                .flatMap(exists -> {
                    if (exists) {
                        // Si ya existe, devolvemos un Mono de error
                        return Mono.error(new MovieAlreadyExistsException("Movie already exists", HttpStatus.CONFLICT));
                    } else {
                        // Si NO existe, la guardamos y retornamos un mensaje
                        return movieDao.saveMovie(movieDto)
                                .thenReturn("Película guardada exitosamente");
                    }
                });
    }


    public Mono<List<Movie>> getAllMovies() {
        return movieDao.findAllMovies().collectList();
    }


    public Mono<String> updateMovie(Movie movie) {
        return movieDao.findMovieByTitle(movie.title()).hasElement()
                .flatMap(exist -> {
                    if (exist) {
                        return movieDao.updateMovie(movie).thenReturn("Película actualizada exitosamente");
                    } else {
                        return Mono.error(new MovieAlreadyExistsException("Movie doesn't exists", HttpStatus.CONFLICT));
                    }
                });
    }

    public Mono<Movie> getMovieByTitle(String title) {
        return movieDao.findMovieByTitle(title).switchIfEmpty(Mono.error(new MovieAlreadyExistsException("Movie doesn't exists", HttpStatus.NOT_FOUND)));
    }


}
