package com.kevin.demo_hexagonal_architecture.application.ports;

import com.kevin.demo_hexagonal_architecture.application.dto.MovieDto;
import com.kevin.demo_hexagonal_architecture.domain.Movie;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface MovieDao {
    Mono<Movie> findMovieByTitle(String title);
    Flux<Movie> findAllMovies();
    Mono<Void> saveMovie(MovieDto movie);
    Mono<Void> updateMovie(Movie newMovie);
    Mono<Void> deleteMovie(Movie oldMovie);
}
