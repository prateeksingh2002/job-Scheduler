package com.jobscheduler1.config;

import com.jobscheduler1.kafka.dto.CallbackMessage;
import com.jobscheduler1.kafka.dto.JobEvent;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ConsumerFactory<String, JobEvent> jobEventConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, "job-scheduler-group");
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.jobscheduler.kafka.dto");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
                new JsonDeserializer<>(JobEvent.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, JobEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, JobEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(jobEventConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, CallbackMessage> callbackMessageConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, "job-scheduler-group");
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.jobscheduler.kafka.dto");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(),
                new JsonDeserializer<>(CallbackMessage.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CallbackMessage> callbackListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CallbackMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(callbackMessageConsumerFactory());
        return factory;
    }
}