package org.example.controller;

import org.example.entity.Movie;
import org.example.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

import static org.example.constants.ApplicationConstants.BY_ID;
import static org.example.constants.ApplicationConstants.MOVIES;

@RestController
@RequestMapping(MOVIES)
public record MovieController(MovieService movieService) {

    @PostMapping
    @ResponseStatus(code = org.springframework.http.HttpStatus.CREATED)
    public Movie save(@RequestBody Movie movie) {
        return movieService.save(movie);
    }

    @GetMapping
    public Movie[] findAll() {
        return movieService.findAll();
    }

    @GetMapping(BY_ID)
    public Movie findById(@PathVariable Long id) {
        return movieService.findById(id);
    }

    @PutMapping(BY_ID)
    public Movie update(@PathVariable Long id, @RequestBody Movie movie) {
        return movieService.update(id, movie);
    }

}
