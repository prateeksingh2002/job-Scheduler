package com.jobscheduler1.model.dto;

import com.jobscheduler1.model.enums.ScheduleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class JobRequest {
    @NotBlank
    private String name;

    private String description;

    @NotBlank
    private String command;

    private String binaryRef;

    @NotNull
    private ScheduleType scheduleType;

    private LocalDateTime scheduledTime;
    private Integer delayMinutes;
    private String cronExpression;
    private List<String> weeklyDays;
    private List<Integer> monthlyDates;
    private String timeZone;
    private Map<String, String> parameters;
}