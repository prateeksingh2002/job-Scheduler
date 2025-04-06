package com.jobscheduler1.kafka.consumer;

import com.jobscheduler1.kafka.dto.CallbackMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallbackConsumer {
    private final KafkaTemplate<String, CallbackMessage> kafkaTemplate;

    public CallbackConsumer(KafkaTemplate<String, CallbackMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.delayed-messages}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeDelayedMessage(CallbackMessage message) {
        log.info("Processing delayed message: {}", message);

        // Here you would typically:
        // 1. Process the message (send notification, trigger action, etc.)
        // 2. Forward to the specified topic if needed

        if (message.getTopic() != null && !message.getTopic().isEmpty()) {
            log.info("Forwarding message to topic: {}", message.getTopic());
            kafkaTemplate.send(message.getTopic(), message);
        }

        // Add your business logic here
    }
}