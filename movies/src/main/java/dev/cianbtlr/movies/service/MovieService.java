package dev.cianbtlr.movies.service;

import dev.cianbtlr.movies.domain.Movie;
import dev.cianbtlr.movies.repo.MovieRepository;
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

    public Optional<Movie> singleMovie(String imdbId) {
        return movieRepository.findMovieByImdbId(imdbId);
    }

    public Movie createMovie(Movie newMovie) {
        return movieRepository.save(newMovie);
    }

    public Optional<Movie> updateMovie(String imdbId, Movie updatedMovie) {
        Optional<Movie> existingMovieOpt = movieRepository.findMovieByImdbId(imdbId);
        if (existingMovieOpt.isPresent()) {
            Movie existingMovie = existingMovieOpt.get();

            existingMovie.setTitle(updatedMovie.getTitle());
            existingMovie.setReleaseDate(updatedMovie.getReleaseDate());
            existingMovie.setTrailerLink(updatedMovie.getTrailerLink());
            existingMovie.setPoster(updatedMovie.getPoster());
            existingMovie.setGenres(updatedMovie.getGenres());
            existingMovie.setBackdrops(updatedMovie.getBackdrops());
            existingMovie.setReviewIds(updatedMovie.getReviewIds());

            movieRepository.save(existingMovie);
            return Optional.of(existingMovie);
        } else {
            return Optional.empty();
        }
    }

    public boolean deleteMovie(String imdbId) {
        Optional<Movie> existingMovieOpt = movieRepository.findMovieByImdbId(imdbId);
        if (existingMovieOpt.isPresent()) {
            movieRepository.delete(existingMovieOpt.get());
            return true;
        } else {
            return false;
        }
    }
}
