package com.example.rabbitproducer.jpa;

import com.example.rabbitproducer.dto.JobStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import javax.swing.text.html.Option;
import lombok.Data;

@Data
@Entity
@Table(name = "job")
public class JobEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobId;
    private String status;
    private String resultPath;
}
