package com.jobscheduler1.service;

import com.jobscheduler1.kafka.dto.CallbackMessage;
import com.jobscheduler1.kafka.producer.CallbackProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class CallbackService {
    private final CallbackProducer callbackProducer;
    private final SchedulingService schedulingService;

    @Async
    public CompletableFuture<Void> scheduleCallback(String message, int delayMinutes, String topic, Map<String, String> metadata) {
        CallbackMessage callbackMessage = new CallbackMessage();
        callbackMessage.setMessage(message);
        callbackMessage.setTopic(topic);
        callbackMessage.setMetadata(metadata);
        callbackMessage.setTimestamp(LocalDateTime.now());

        if (delayMinutes <= 0) {
            // Send immediately
            callbackProducer.sendCallbackMessage(callbackMessage);
        } else {
            // Schedule for delayed sending
            schedulingService.scheduleDelayedMessage(callbackMessage, delayMinutes);
        }

        return CompletableFuture.completedFuture(null);
    }
}