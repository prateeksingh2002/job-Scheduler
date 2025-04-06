package com.jobscheduler1.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CallbackMessage {
    private String message;
    private String topic;
    private Map<String, String> metadata;
    private LocalDateTime timestamp;
}