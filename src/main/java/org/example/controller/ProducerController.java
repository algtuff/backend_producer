package org.example.controller;

import org.example.dto.WinnerMaxMinDTO;
import org.example.entity.Producer;
import org.example.service.ProducerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producers")
public record ProducerController(ProducerService producerService) {

    @PostMapping
    public Producer save(@RequestBody Producer producer) {
        return producerService.save(producer);
    }

    @GetMapping
    public Producer[] findAll() {
        return producerService.findAll();
    }

    @GetMapping("/winners")
    public WinnerMaxMinDTO findWinnerMaxMinInterval() {
        return producerService.findWinnerMaxMinInterval();
    }

}
