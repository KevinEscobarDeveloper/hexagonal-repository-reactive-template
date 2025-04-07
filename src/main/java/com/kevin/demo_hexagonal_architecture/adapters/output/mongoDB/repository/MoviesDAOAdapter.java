package com.kevin.demo_hexagonal_architecture.adapters.output.mongoDB.repository;

import com.kevin.demo_hexagonal_architecture.adapters.output.mongoDB.entities.MovieEntity;
import com.kevin.demo_hexagonal_architecture.adapters.output.mongoDB.mappers.MovieMapper;
import com.kevin.demo_hexagonal_architecture.application.ports.MovieDao;
import com.kevin.demo_hexagonal_architecture.application.dto.MovieDto;
import com.kevin.demo_hexagonal_architecture.domain.Movie;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class MoviesDAOAdapter implements MovieDao {

    private final MovieReactiveRepository movieReactiveRepository;
    private final MovieMapper movieMapper;

    @Override
    public Mono<Movie> findMovieByTitle(String title) {
        return movieReactiveRepository.findByTitle(title).map(movieMapper::toDomain);
    }

    @Override
    public Flux<Movie> findAllMovies() {
        return movieReactiveRepository.findAll().map(movieMapper::toDomain);
    }

    @Override
    public Mono<Void> saveMovie(MovieDto movie) {
        MovieEntity entityToSave = movieMapper.dtoToMovieEntity(movie);
        return movieReactiveRepository.save(entityToSave).then(Mono.empty());
    }

    @Override
    public Mono<Void> updateMovie(Movie newMovie) {
        MovieEntity entityToUpdate = movieMapper.toMovie(newMovie);
        return movieReactiveRepository.save(entityToUpdate).then(Mono.empty());
    }

    @Override
    public Flux<Movie> searchMovies(String title) {
        return movieReactiveRepository.searchMovies(title).map(movieMapper::toDomain);
    }

    @Override
    public Mono<Void> deleteMovie(Movie oldMovie) {
        return movieReactiveRepository.deleteById(oldMovie.id()).then(Mono.empty());
    }
}
