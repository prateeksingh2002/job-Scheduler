package com.jobscheduler1.controller;

import com.jobscheduler1.model.dto.ExecutionResponse;
import com.jobscheduler1.service.ExecutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/executions")
@RequiredArgsConstructor
public class ExecutionController {
    private final ExecutionService executionService;

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ExecutionResponse>> getExecutionsForJob(@PathVariable UUID jobId) {
        List<ExecutionResponse> responses = executionService.getExecutionsForJob(jobId);
        return ResponseEntity.ok(responses);
    }
}