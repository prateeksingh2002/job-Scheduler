package com.jobscheduler1.kafka.producer;

import com.jobscheduler1.kafka.dto.JobEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobEventProducer {
    private final KafkaTemplate<String, JobEvent> kafkaTemplate;

    public void sendJobEvent(JobEvent event) {
        kafkaTemplate.send("job-events", event);
    }
}