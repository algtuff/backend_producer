package org.example.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.example.dto.WinnerMaxMinDTO;
import org.example.entity.Producer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProducerServiceTest {

    @Autowired
    private MockMvc mockMvc;

    private String objectToJsonString(Object object) throws Exception {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(object);
    }

    private String getRamdomString() {
        return String.valueOf(System.currentTimeMillis());
    }

    private Producer createProducer() {
        Producer producer = new Producer();
        producer.setName(getRamdomString());
        return producer;
    }

    @Test
    public void testSave() throws Exception {
        mockMvc.perform(
            post("/producers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJsonString(createProducer())))
            .andDo(print())
            .andExpect(status().isCreated());
    }

    @Test
    public void testFindAll() throws Exception {
        mockMvc.perform(get("/producers"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("id")));
    }

    @Test
    public void testFindById() throws Exception {
        final var result = mockMvc.perform(get("/producers/1"))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

        final String content = result.getResponse().getContentAsString();
        Producer producer = new ObjectMapper().readValue(content, Producer.class);
        Assertions.assertNotNull(producer);
        Assertions.assertEquals(1, producer.getId());
    }

    @Test
    public void findProducerWinnerMinMaxInterval() throws Exception {
        final var result = mockMvc.perform(get("/producers/winner/min-max-interval"))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

        final String content = result.getResponse().getContentAsString();
        WinnerMaxMinDTO winnerMaxMinDTO = new ObjectMapper().readValue(content, WinnerMaxMinDTO.class);
        Assertions.assertNotNull(winnerMaxMinDTO);
        Assertions.assertNotNull(winnerMaxMinDTO.getMin());
        Assertions.assertNotNull(winnerMaxMinDTO.getMax());
    }


}
