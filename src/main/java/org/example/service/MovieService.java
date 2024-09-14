package org.example.service;

import org.example.entity.Movie;
import org.example.exception.MovieNotFoundException;
import org.example.repository.MovieRepository;
import org.springframework.stereotype.Service;

import static org.example.constants.ApplicationConstants.EXCEPTION_MOVIE_NOT_FOUND;

@Service
public record MovieService(MovieRepository movieRepository) {

    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }


    public Movie[] findAll() {
        return movieRepository.findAll().toArray(new Movie[0]);
    }

    public Movie findById(Long id) {
        Movie movie = movieRepository.findById(id).orElse(null);
        if (movie == null) {
            throw new MovieNotFoundException(EXCEPTION_MOVIE_NOT_FOUND);
        }
        return movie;
    }

    public Movie update(Long id, Movie movie) {
        Movie movieToUpdate = movieRepository.findById(id).orElse(null);
        if (movieToUpdate == null) {
            throw new MovieNotFoundException(EXCEPTION_MOVIE_NOT_FOUND);
        }
        movieToUpdate.setMovieYear(movie.getMovieYear());
        movieToUpdate.setProducers(movie.getProducers());
        movieToUpdate.setWinner(movie.isWinner());
        movieToUpdate.setStudio(movie.getStudio());
        movieToUpdate.setTitle(movie.getTitle());
        return movieRepository.save(movieToUpdate);
    }

}
