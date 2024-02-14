package com.example.rabbitconsumer.repo;

import com.example.rabbitconsumer.jpa.JobEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<JobEntity, Long> {
    Optional<JobEntity> findByJobId(String jobId);
}
