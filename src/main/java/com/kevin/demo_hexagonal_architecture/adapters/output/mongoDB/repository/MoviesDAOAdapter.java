package com.kevin.demo_hexagonal_architecture.adapters.output.mongoDB.repository;

import com.kevin.demo_hexagonal_architecture.application.dao.MovieDao;
import com.kevin.demo_hexagonal_architecture.application.dto.MovieDto;
import com.kevin.demo_hexagonal_architecture.domain.Movie;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MoviesDAOAdapter implements MovieDao {
    @Override
    public Mono<Movie> findMovieByTitle(String title) {
        return null;
    }

    @Override
    public Flux<Movie> findAllMovies() {
        return null;
    }

    @Override
    public Mono<Void> saveMovie(MovieDto movie) {
        return null;
    }

    @Override
    public Mono<Void> updateMovie(Movie newMovie) {
        return null;
    }

    @Override
    public Mono<Void> deleteMovie(Movie oldMovie) {
        return null;
    }
}
