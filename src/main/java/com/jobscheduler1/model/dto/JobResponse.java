package com.jobscheduler1.model.dto;

import com.jobscheduler1.model.enums.JobStatus;
import com.jobscheduler1.model.enums.ScheduleType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
public class JobResponse {
    private UUID id;
    private String name;
    private String description;
    private String command;
    private String binaryRef;
    private ScheduleType scheduleType;
    private String scheduleDetails;
    private JobStatus status;
    private String timeZone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Map<String, String> parameters;
}