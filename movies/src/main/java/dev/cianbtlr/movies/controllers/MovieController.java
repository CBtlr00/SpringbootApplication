package dev.cianbtlr.movies.controllers;

import dev.cianbtlr.movies.domain.Movie;
import dev.cianbtlr.movies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        return new ResponseEntity<>(movieService.allMovies(), HttpStatus.OK);
    }

    @GetMapping("/{imdbId}")
    public ResponseEntity<Optional<Movie>> getSingleMovie(@PathVariable String imdbId) {
        return new ResponseEntity<>(movieService.singleMovie(imdbId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie newMovie) {
        Movie createdMovie = movieService.createMovie(newMovie);
        return new ResponseEntity<>(createdMovie, HttpStatus.CREATED);
    }

    @PutMapping("/{imdbId}")
    public ResponseEntity<Optional<Movie>> updateMovie(@PathVariable String imdbId, @RequestBody Movie updatedMovie) {
        Optional<Movie> updatedMovieOpt = movieService.updateMovie(imdbId, updatedMovie);
        if (updatedMovieOpt.isPresent()) {
            return new ResponseEntity<>(updatedMovieOpt, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{imdbId}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String imdbId) {
        boolean isDeleted = movieService.deleteMovie(imdbId);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}