package com.jobscheduler1.kafka.consumer;

import com.jobscheduler1.kafka.dto.JobEvent;
import com.jobscheduler1.model.entity.JobEntity;
import com.jobscheduler1.model.enums.ExecutionStatus;
import com.jobscheduler1.model.enums.JobStatus;
import com.jobscheduler1.repository.JobRepository;
import com.jobscheduler1.service.ExecutionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobStatusConsumer {
    private final JobRepository jobRepository;
    private final ExecutionService executionService;

    @KafkaListener(
            topics = "${spring.kafka.topic.job-events}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeJobEvent(JobEvent event) {
        log.info("Received Job Event: {}", event);

        Optional<JobEntity> jobOptional = jobRepository.findById(event.getJobId());
        if (jobOptional.isEmpty()) {
            log.warn("Job not found for event: {}", event);
            return;
        }

        JobEntity job = jobOptional.get();

        // Update job status
        job.setStatus(event.getStatus());
        jobRepository.save(job);

        // Update execution status if needed
        if (event.getStatus() == JobStatus.COMPLETED || event.getStatus() == JobStatus.FAILED) {
            executionService.updateExecutionStatus(
                    job.getId(),
                    event.getStatus() == JobStatus.COMPLETED
                            ? ExecutionStatus.COMPLETED
                            : ExecutionStatus.FAILED,
                    event.getOutput(),
                    event.getError()
            );
        }
    }
}