package org.example.controller;

import org.example.entity.Movie;
import org.example.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/movies")
public record MovieController(MovieService movieService) {

    @PostMapping
    public Movie save(@RequestBody Movie movie) {
        return movieService.save(movie);
    }

    @GetMapping
    public Movie[] findAll() {
        return movieService.findAll();
    }

}
