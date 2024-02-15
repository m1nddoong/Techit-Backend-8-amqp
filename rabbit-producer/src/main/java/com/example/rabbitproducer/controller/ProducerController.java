package com.example.rabbitproducer.controller;

import com.example.rabbitproducer.dto.JobRequest;
import com.example.rabbitproducer.dto.JobStatus;
import com.example.rabbitproducer.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProducerController {
    private final ProducerService service;

    /*@PostMapping("/make-job")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void makeJob(
            @RequestParam("message")
            String message
    ) {
        service.send(message);
    }*/

    @PostMapping("/make-job")
    public JobStatus makeJob(
            @RequestBody
            JobRequest dto
    ) {
        return service.send(dto);
    }

    @GetMapping("/get-job")
    public JobStatus getJob(
            @RequestParam("job")
            String jobId
    ) {
        return service.getJobStatus(jobId);
    }

    @PostMapping("/topic")
    public void topic(
            @RequestParam("topic")
            String topic,
            @RequestParam("message")
            String message
    ) {
        service.sendTopic(topic, message);
    }
}
