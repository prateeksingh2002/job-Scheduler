package com.jobscheduler1.controller;

import com.jobscheduler1.model.dto.JobRequest;
import com.jobscheduler1.model.dto.JobResponse;
import com.jobscheduler1.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;

    @PostMapping
    public ResponseEntity<JobResponse> createJob(@Valid @RequestBody JobRequest jobRequest) {
        JobResponse response = jobService.createJob(jobRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJob(@PathVariable UUID id) {
        JobResponse response = jobService.getJob(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<JobResponse>> getAllJobs() {
        List<JobResponse> responses = jobService.getAllJobs();
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable UUID id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }
}