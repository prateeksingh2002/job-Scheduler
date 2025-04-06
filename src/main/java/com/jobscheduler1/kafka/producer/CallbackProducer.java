package com.jobscheduler1.kafka.producer;

import com.jobscheduler1.kafka.dto.CallbackMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CallbackProducer {
    private final KafkaTemplate<String, CallbackMessage> kafkaTemplate;

    public void sendCallbackMessage(CallbackMessage message) {
        kafkaTemplate.send("callback-messages", message);
    }
}