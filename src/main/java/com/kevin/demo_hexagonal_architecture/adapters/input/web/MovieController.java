package com.kevin.demo_hexagonal_architecture.adapters.input.web;

import com.kevin.demo_hexagonal_architecture.application.dto.MovieDto;
import com.kevin.demo_hexagonal_architecture.application.usecases.MovieUseCase;
import com.kevin.demo_hexagonal_architecture.domain.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieUseCase movieUseCase;

    @GetMapping
    public ResponseEntity<?> getAllMovies(){
        return ResponseEntity.ok(movieUseCase.getAllMovies());
    }

    @GetMapping("/{title}")
    public ResponseEntity<?> getMovieByTitle(@PathVariable(name = "title") String title){
        return ResponseEntity.ok(movieUseCase.getMovieByTitle(title));
    }

    @PostMapping
    public ResponseEntity<?> saveMovie(@RequestBody MovieDto movieDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(movieUseCase.saveMovie(movieDto));
    }

    @PutMapping
    public ResponseEntity<?> updateMovie(@RequestBody Movie movie){
        return ResponseEntity.status(HttpStatus.OK).body(movieUseCase.updateMovie(movie));
    }
}
