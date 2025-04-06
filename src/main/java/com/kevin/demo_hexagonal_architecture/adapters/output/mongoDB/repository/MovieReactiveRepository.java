package com.kevin.demo_hexagonal_architecture.adapters.output.mongoDB.repository;

import com.kevin.demo_hexagonal_architecture.adapters.output.mongoDB.entities.MovieEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface MovieReactiveRepository extends ReactiveMongoRepository<MovieEntity, Long> {
    Mono<MovieEntity> findByTitle(String title);

    @Query("{ '$or': [ " +
            "{ 'title': { $regex: ?0, $options: 'i' } }, " +
            "{ 'directorName': { $regex: ?0, $options: 'i' } }, " +
            "{ 'description': { $regex: ?0, $options: 'i' } } " +
            "] }")
    Flux<MovieEntity> searchMovies(String keyword);
}
