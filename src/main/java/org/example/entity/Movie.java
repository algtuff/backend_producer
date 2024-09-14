package org.example.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Movie {

    @Id
    @GeneratedValue
    private Long movieId;
    private String title;
    private Integer movieYear;
    private String studio;
    private boolean winner;

    @ManyToMany(cascade = CascadeType.MERGE)
    private List<Producer> producers;

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long id) {
        this.movieId = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getMovieYear() {
        return movieYear;
    }

    public void setMovieYear(Integer year) {
        this.movieYear = year;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public List<Producer> getProducers() {
        return producers;
    }

    public void setProducers(List<Producer> producers) {
        this.producers = producers;
    }

}
