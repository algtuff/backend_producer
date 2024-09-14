package org.example.service;

import jakarta.persistence.Tuple;
import org.example.dto.WinnerDTO;
import org.example.dto.WinnerMaxMinDTO;
import org.example.entity.Producer;
import org.example.repository.ProducerRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public record ProducerService(ProducerRepository producerRepository) {

    public Producer save(Producer producer) {
        return producerRepository.save(producer);
    }

    public Producer[] findAll() {
        return producerRepository.findAll().toArray(new Producer[0]);
    }

    private WinnerDTO parseTuple(Tuple tuple) {
        //TODO criar constantes para os nomes das colunas
        String name = (String) tuple.get("name");
        Integer maxYear = (Integer) tuple.get("max_year");
        Integer minYear = (Integer) tuple.get("min_year");
        Integer interval = (Integer) tuple.get("inter");
        return new WinnerDTO(name, minYear, maxYear, interval);
    }

    public WinnerMaxMinDTO findWinnerMaxMinInterval() {
        WinnerMaxMinDTO winnerMaxMinDTO = new WinnerMaxMinDTO();

        var tupleListMax = producerRepository.findWinnerMaxInterval();
        WinnerDTO[] winnerMax = tupleListMax.stream()
            .map(this::parseTuple)
            .toArray(WinnerDTO[]::new);
        winnerMaxMinDTO.setMax(winnerMax);

        var tupleListMin = producerRepository.findWinnerMinInterval();
        WinnerDTO[] winnerMin = tupleListMin.stream()
            .map(this::parseTuple)
            .toArray(WinnerDTO[]::new);
        winnerMaxMinDTO.setMin(winnerMin);

        return winnerMaxMinDTO;
    }
}
