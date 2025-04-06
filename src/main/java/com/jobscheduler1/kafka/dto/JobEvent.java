package com.jobscheduler1.kafka.dto;

import com.jobscheduler1.model.enums.JobStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobEvent {
    private UUID jobId;
    private String jobName;
    private JobStatus status;
    private LocalDateTime timestamp;
    private String message;
    private String output;
    private String error;
}