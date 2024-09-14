package org.example.test;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.example.entity.Movie;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieServiceTest {

    @Autowired
    private MockMvc mockMvc;

    private String objectToJsonString(Object object) throws Exception {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(object);
    }

    private String getRamdomString() {
        return String.valueOf(System.currentTimeMillis());
    }

    private Movie createMovie() {
        Movie movie = new Movie();
        movie.setTitle(getRamdomString());
        movie.setStudio(getRamdomString());
        movie.setMovieYear(2021);
        movie.setWinner(true);
        return movie;
    }

    @Test
    public void testSaveWithoutProducer() throws Exception {
        mockMvc.perform(
            post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJsonString(createMovie())))
            .andDo(print())
            .andExpect(status().isCreated());
    }

    @Test
    public void testFindAll() throws Exception {
        mockMvc.perform(get("/movies"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("movieId")));
    }

    @Test
    public void testFindById() throws Exception {
        mockMvc.perform(get("/movies/1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("\"movieId\":1")));
    }


}
