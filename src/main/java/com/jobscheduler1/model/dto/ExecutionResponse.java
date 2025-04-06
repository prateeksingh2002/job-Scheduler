package com.jobscheduler1.model.dto;

import com.jobscheduler1.model.enums.ExecutionStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ExecutionResponse {
    private UUID id;
    private UUID jobId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ExecutionStatus status;
    private String output;
    private String error;
    private LocalDateTime createdAt;
}