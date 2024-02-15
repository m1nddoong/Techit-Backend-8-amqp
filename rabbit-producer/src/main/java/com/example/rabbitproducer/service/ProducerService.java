package com.example.rabbitproducer.service;


import com.example.rabbitproducer.dto.JobPayload;
import com.example.rabbitproducer.dto.JobRequest;
import com.example.rabbitproducer.dto.JobStatus;
import com.example.rabbitproducer.jpa.JobEntity;
import com.example.rabbitproducer.repo.JobRepository;
import com.google.gson.Gson;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProducerService {
    // 요청을 보내기 위한 인터페이스
    private final RabbitTemplate rabbitTemplate;
    private final Queue jobQueue;
    private final JobRepository jobRepository;
    private final Gson gson;

    public JobStatus send(JobRequest request) {
        // Consumer가 확인할 메세지를 만든다.
        String jobId = UUID.randomUUID().toString();
        String filename = request.getFilename();
        JobPayload payload = new JobPayload(
                jobId,
                filename,
                // 서버의 어디에 파일을 저장해 두었는지
                String.format("/media/user-uploaded/raw/%s", filename)
        );
        // 전달한 정보를 데이터베이스에 기록
        JobEntity newJob = new JobEntity();
        newJob.setJobId(jobId);
        newJob.setStatus("WAIT");
        // resultPath는 Consumer가 기록해준다.
        JobStatus jobStatus = JobStatus.fromEntity(jobRepository.save(newJob));
        // 모든 정보가 준비되면 메세지를 브로커에 전송
        rabbitTemplate.convertAndSend(jobQueue.getName(), gson.toJson(payload));
        log.info("Sent Job: {}", jobId);
        // 사용자에게 응답
        return jobStatus;
    }

    public JobStatus getJobStatus(String jobId) {
        return JobStatus.fromEntity(jobRepository.findByJobId(jobId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    public void send(String message) {
        rabbitTemplate.convertAndSend(
                jobQueue.getName(), message
        );
        log.info("sent message: {}", message);
    }

    private final TopicExchange topicExchange;

    public void sendTopic(String topic, String message) {
        rabbitTemplate.convertAndSend(
                topicExchange.getName(),
                topic,
                message
        );
    }
}
