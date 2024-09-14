package org.example.utils;

import jakarta.annotation.PostConstruct;
import org.example.controller.MovieController;
import org.example.controller.ProducerController;
import org.example.entity.Movie;
import org.example.entity.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.example.constants.ApplicationConstants.*;

@Component
public class FileMigration {

    @Autowired
    private MovieController movieController;

    @Autowired
    private ProducerController producerController;


    private String getValue(final String[] values, final Integer index) {
        try {
            return values[index];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    private List<Producer> ToProducer(String[] producers) {
        return Stream.of(producers)
            .map(producer -> {
                Producer producerDTO = new Producer();
                producerDTO.setName(producer);
                return producerDTO;
            })
            .toList();
    }

    private Movie lineToProducer(String line) {
        String[] values = line.split(CSV_SPLITTER);
        Movie movie = new Movie();

        final String year = this.getValue(values, YEAR_INDEX);
        movie.setMovieYear(
            year != null ? Integer.parseInt(year) : null
        );
        movie.setTitle(values[TITLE_INDEX]);
        movie.setStudio(values[STUDIO_INDEX]);

        boolean winner = this.getValue(values, WINNER_INDEX) != null;
        movie.setWinner(winner);

        String[] producers = values[PRODUCERS_INDEX]
            .replace(" and ", ", ")
            .split(", ");

        movie.setProducers(ToProducer(producers));

        return movie;
    }

    private Movie[] listMovies() {
        try {
            Path path = Paths.get(ClassLoader.getSystemResource(CSV_FILE).toURI());
             try (Stream<String> linesStream = Files.lines(path)) {
                 return linesStream
                     .skip(1)
                     .toList()
                     .stream()
                     .map(this::lineToProducer)
                     .toArray(Movie[]::new);
             }

        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    private void migrate() {
        System.out.println(MSG_MIGRATING_DATA);
        Movie[] movies = listMovies();
        final Producer[] producers = Arrays.stream(movies)
            .map(Movie::getProducers)
            .flatMap(List::stream)
            .toArray(Producer[]::new);

        final Producer[] producersWithoutDuplicates = Arrays.stream(producers)
            .collect(()->new ArrayList<Producer>(),
                (producerList, producer)->{
                    if(producerList.isEmpty()){
                        producerList.add(producer);
                        return;
                    }
                    boolean isDuplicate = producerList.stream()
                        .anyMatch(p -> p.getName().equals(producer.getName()));
                    if(!isDuplicate){
                        producerList.add(producer);
                    }
                }, ArrayList::addAll)
            .toArray(Producer[]::new);
        for (Producer producer : producersWithoutDuplicates) {
            producerController.save(producer);
        }
        for (Movie movie : movies) {
                movie.getProducers()
                .stream()
                .filter(producer -> producer.getId() == null)
                .forEach(producer -> {
                    final Producer producerWithId = Stream.of(producersWithoutDuplicates)
                        .filter(p -> p.getName().equals(producer.getName()))
                        .findFirst()
                        .orElse(null);
                    producer.setId(producerWithId.getId());
            });
            movieController.save(movie);
        }
        System.out.println(MSG_DATA_MIGRATED);
    }

}
