package com.example.rabbitproducer.repo;

import com.example.rabbitproducer.jpa.JobEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<JobEntity, Long> {
    Optional<JobEntity> findByJobId(String jobId);
}
