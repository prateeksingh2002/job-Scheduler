package com.jobscheduler1.service;

import com.jobscheduler1.model.dto.JobRequest;
import com.jobscheduler1.model.dto.JobResponse;
import com.jobscheduler1.model.entity.JobEntity;
import com.jobscheduler1.model.enums.JobStatus;
import com.jobscheduler1.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    private final SchedulingService schedulingService;
    private final ModelMapper modelMapper;

    @Transactional
    public JobResponse createJob(JobRequest jobRequest) {
        JobEntity job = modelMapper.map(jobRequest, JobEntity.class);
        job.setStatus(JobStatus.CREATED);

        JobEntity savedJob = jobRepository.save(job);

        schedulingService.scheduleJob(savedJob);

        return modelMapper.map(savedJob, JobResponse.class);
    }

    public JobResponse getJob(UUID id) {
        JobEntity job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        return modelMapper.map(job, JobResponse.class);
    }

    public List<JobResponse> getAllJobs() {
        return jobRepository.findAll().stream()
                .map(job -> modelMapper.map(job, JobResponse.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateJobStatus(UUID jobId, JobStatus status) {
        JobEntity job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        job.setStatus(status);
        jobRepository.save(job);
    }

    @Transactional
    public void deleteJob(UUID id) {
        JobEntity job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        schedulingService.cancelJob(job);
        jobRepository.delete(job);
    }
}