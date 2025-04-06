package com.jobscheduler1.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Value("${spring.kafka.topic.job-events}")
    private String jobEventsTopic;

    @Value("${spring.kafka.topic.callback-messages}")
    private String callbackMessagesTopic;

    @Bean
    public NewTopic jobEventsTopic() {
        return TopicBuilder.name(jobEventsTopic)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic callbackMessagesTopic() {
        return TopicBuilder.name(callbackMessagesTopic)
                .partitions(3)
                .replicas(1)
                .build();
    }
}