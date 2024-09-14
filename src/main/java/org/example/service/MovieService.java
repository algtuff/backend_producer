package org.example.service;

import org.example.entity.Movie;
import org.example.repository.MovieRepository;
import org.springframework.stereotype.Service;

@Service
public record MovieService(MovieRepository movieRepository) {

    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie[] findAll() {
        return movieRepository.findAll().toArray(new Movie[0]);
    }




}
