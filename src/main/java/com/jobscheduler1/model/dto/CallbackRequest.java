package com.jobscheduler1.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;

@Data
public class CallbackRequest {
    @NotBlank(message = "Message cannot be blank")
    private String message;

    @Min(value = 0, message = "Delay cannot be negative")
    private int delayMinutes;

    @NotBlank(message = "Topic cannot be blank")
    private String topic;

    @NotNull(message = "Metadata cannot be null")
    private Map<String, String> metadata;
}