package org.example.service;

import jakarta.persistence.Tuple;
import org.example.dto.WinnerDTO;
import org.example.dto.WinnerMaxMinDTO;
import org.example.entity.Producer;
import org.example.exception.ProducerNotFoundException;
import org.example.repository.ProducerRepository;
import org.springframework.stereotype.Service;

import static org.example.constants.ApplicationConstants.*;

@Service
public record ProducerService(ProducerRepository producerRepository) {

    public Producer save(Producer producer) {
        return producerRepository.save(producer);
    }

    public Producer[] findAll() {
        return producerRepository.findAll().toArray(new Producer[0]);
    }

    public Producer findById(Long id) {
        Producer producer = producerRepository.findById(id).orElse(null);
        if (producer == null) {
            throw new ProducerNotFoundException(EXCEPTION_PRODUCER_NOT_FOUND);
        }
        return producer;
    }

    private WinnerDTO parseTuple(Tuple tuple) {
        String name = (String) tuple.get(COLUMN_NAME);
        Integer maxYear = (Integer) tuple.get(COLUMN_MAX_YEAR);
        Integer minYear = (Integer) tuple.get(COLUMN_MIN_YEAR);
        Integer interval = (Integer) tuple.get(COLUMN_INTER);
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

    public Producer update(Long id, Producer producer) {
        Producer producerToUpdate = producerRepository.findById(id).orElse(null);
        if (producerToUpdate == null) {
            throw new ProducerNotFoundException(EXCEPTION_PRODUCER_NOT_FOUND);
        }
        producerToUpdate.setName(producer.getName());
        return producerRepository.save(producerToUpdate);
    }

}
