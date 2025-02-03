package com.kevin.demo_hexagonal_architecture.adapters.output.mongoDB.mappers;

import com.kevin.demo_hexagonal_architecture.adapters.output.mongoDB.entities.MovieEntity;
import com.kevin.demo_hexagonal_architecture.application.dto.MovieDto;
import com.kevin.demo_hexagonal_architecture.domain.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    Movie toDomain(MovieEntity entity);

    MovieEntity toMovie(Movie movie);

    List<Movie> toDomainList(List<MovieEntity> movieEntities);

    List<MovieEntity> toMovieEntitiesList(List<MovieEntity> movies);

    MovieEntity dtoToMovieEntity(MovieDto entity);
}
