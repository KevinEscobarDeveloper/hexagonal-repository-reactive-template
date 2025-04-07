package com.kevin.demo_hexagonal_architecture.adapters.output.mongoDB.mappers;

import com.kevin.demo_hexagonal_architecture.adapters.output.mongoDB.entities.MovieEntity;
import com.kevin.demo_hexagonal_architecture.application.dto.MovieDto;
import com.kevin.demo_hexagonal_architecture.domain.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    Movie toDomain(MovieEntity entity);
    @Mapping(target = "createdDate", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "modifiedDate", expression = "java(java.time.LocalDate.now())")
    MovieEntity toMovie(Movie movie);

    List<Movie> toDomainList(List<MovieEntity> movieEntities);

    List<MovieEntity> toMovieEntitiesList(List<MovieEntity> movies);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    MovieEntity dtoToMovieEntity(MovieDto entity);

    MovieDto toDto(MovieEntity entity);
    MovieDto domainToDto(Movie entity);
}
