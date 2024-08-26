package dev.cianbtlr.movies.service;

import dev.cianbtlr.movies.domain.Movie;
import dev.cianbtlr.movies.repo.MovieRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> allMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> singleMovie(ObjectId id) { return movieRepository.findById(id); }
}
