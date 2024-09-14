package org.example.controller;

import org.example.dto.WinnerMaxMinDTO;
import org.example.entity.Producer;
import org.example.service.ProducerService;
import org.springframework.web.bind.annotation.*;

import static org.example.constants.ApplicationConstants.*;

@RestController
@RequestMapping(PRODUCERS)
public record ProducerController(ProducerService producerService) {

    @PostMapping
    @ResponseStatus(code = org.springframework.http.HttpStatus.CREATED)
    public Producer save(@RequestBody Producer producer) {
        return producerService.save(producer);
    }

    @GetMapping
    public Producer[] findAll() {
        return producerService.findAll();
    }

    @GetMapping(BY_ID)
    public Producer findById(@PathVariable Long id) {
        return producerService.findById(id);
    }

    @PutMapping(BY_ID)
    public Producer update(@PathVariable Long id, @RequestBody Producer producer) {
        return producerService.update(id, producer);
    }

    @GetMapping(WINNER + MIN_MAX_INTERVAL)
    public WinnerMaxMinDTO findWinnerMaxMinInterval() {
        return producerService.findWinnerMaxMinInterval();
    }

}
