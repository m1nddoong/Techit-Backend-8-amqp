package com.example.rabbitproducer.dto;

import com.example.rabbitproducer.jpa.JobEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

// 사용자에게 현재 처리중인 작업의 처리상태를
// 공유하기 위한 DTO
@Data
@AllArgsConstructor
public class JobStatus {
    private String jobId;
    private String status;
    private String resultPath;

    public static JobStatus fromEntity(JobEntity entity) {
        return new JobStatus(
                entity.getJobId(),
                entity.getStatus(),
                entity.getResultPath()
        );
    }

}
