package com.jobscheduler1.service;

import com.jobscheduler1.model.dto.ExecutionResponse;
import com.jobscheduler1.model.entity.ExecutionEntity;
import com.jobscheduler1.model.entity.JobEntity;
import com.jobscheduler1.model.enums.ExecutionStatus;
import com.jobscheduler1.repository.ExecutionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExecutionService {
    private final ExecutionRepository executionRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public ExecutionEntity startExecution(JobEntity job) {
        ExecutionEntity execution = ExecutionEntity.builder()
                .job(job)
                .startTime(LocalDateTime.now())
                .status(ExecutionStatus.STARTED)
                .build();
        return executionRepository.save(execution);
    }

    @Transactional
    public void updateExecutionStatus(UUID jobId, ExecutionStatus status, String output, String error) {
        // Find the latest execution for this job
        List<ExecutionEntity> executions = executionRepository.findByJobIdOrderByStartTimeDesc(jobId);
        if (executions.isEmpty()) {
            throw new RuntimeException("No execution found for job: " + jobId);
        }

        ExecutionEntity execution = executions.get(0);
        completeExecution(execution, status, output, error);
    }

    @Transactional
    public void completeExecution(ExecutionEntity execution, ExecutionStatus status, String output, String error) {
        execution.setEndTime(LocalDateTime.now());
        execution.setStatus(status);
        execution.setOutput(output);
        execution.setError(error);
        executionRepository.save(execution);
    }

    public List<ExecutionResponse> getExecutionsForJob(UUID jobId) {
        return executionRepository.findByJobId(jobId).stream()
                .map(execution -> modelMapper.map(execution, ExecutionResponse.class))
                .collect(Collectors.toList());
    }
}