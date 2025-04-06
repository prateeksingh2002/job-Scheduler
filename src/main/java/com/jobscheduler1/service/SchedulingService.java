package com.jobscheduler1.service;

import com.jobscheduler1.kafka.dto.CallbackMessage;
import com.jobscheduler1.kafka.producer.JobEventProducer;
import com.jobscheduler1.model.entity.JobEntity;
import com.jobscheduler1.model.enums.JobStatus;
import com.jobscheduler1.model.enums.ScheduleType;
import com.jobscheduler1.util.CronExpressionGenerator;
import com.jobscheduler1.util.TimeZoneUtil;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SchedulingService {
    private final Scheduler scheduler;
    private final JobService jobService;
    private final ExecutionService executionService;
    private final BinaryStorageService binaryStorageService;
    private final JobEventProducer jobEventProducer;

    @Transactional
    public void scheduleJob(JobEntity job) {
        try {
            JobDetail jobDetail = buildJobDetail(job);
            Trigger trigger = buildJobTrigger(job, jobDetail);

            scheduler.scheduleJob(jobDetail, trigger);

            job.setStatus(JobStatus.SCHEDULED);
            jobService.updateJobStatus(job.getId(), JobStatus.SCHEDULED);

        } catch (SchedulerException e) {
            throw new RuntimeException("Failed to schedule job", e);
        }
    }

    private JobDetail buildJobDetail(JobEntity job) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobId", job.getId().toString());

        return JobBuilder.newJob(JobExecutionJob.class)
                .withIdentity(job.getId().toString())
                .withDescription(job.getDescription())
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger buildJobTrigger(JobEntity job, JobDetail jobDetail) {
        if (job.getScheduleType() == ScheduleType.IMMEDIATE) {
            return TriggerBuilder.newTrigger()
                    .forJob(jobDetail)
                    .withIdentity(job.getId().toString() + "_trigger")
                    .withDescription("Immediate Trigger")
                    .startNow()
                    .build();
        } else if (job.getScheduleType() == ScheduleType.SPECIFIC_TIME) {
            LocalDateTime scheduledTime = TimeZoneUtil.convertToUTC(job.getScheduledTime(), job.getTimeZone());
            return TriggerBuilder.newTrigger()
                    .forJob(jobDetail)
                    .withIdentity(job.getId().toString() + "_trigger")
                    .withDescription("Specific Time Trigger")
                    .startAt(Date.from(scheduledTime.atZone(ZoneId.systemDefault()).toInstant()))
                    .build();
        } else if (job.getScheduleType() == ScheduleType.RECURRING) {
            String cronExpression = CronExpressionGenerator.generateCronExpression(job);
            return TriggerBuilder.newTrigger()
                    .forJob(jobDetail)
                    .withIdentity(job.getId().toString() + "_trigger")
                    .withDescription("Recurring Trigger")
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                    .build();
        } else {
            throw new IllegalArgumentException("Unsupported schedule type: " + job.getScheduleType());
        }
    }

    public void cancelJob(JobEntity job) {
        try {
            scheduler.deleteJob(new JobKey(job.getId().toString()));
        } catch (SchedulerException e) {
            throw new RuntimeException("Failed to cancel job", e);
        }
    }

    @Scheduled(fixedRate = 60000)
    public void checkDelayedJobs() {
        // Implementation for checking delayed jobs
    }

    public void scheduleDelayedMessage(CallbackMessage message, int delayMinutes) {
        JobDetail jobDetail = buildCallbackJobDetail(message);
        Trigger trigger = buildCallbackTrigger(message, delayMinutes, jobDetail);

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException("Failed to schedule delayed message", e);
        }
    }

    private JobDetail buildCallbackJobDetail(CallbackMessage message) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("message", message);

        return JobBuilder.newJob(CallbackJob.class)
                .withIdentity(UUID.randomUUID().toString())
                .withDescription("Delayed callback message")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    private Trigger buildCallbackTrigger(CallbackMessage message, int delayMinutes, JobDetail jobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(UUID.randomUUID().toString())
                .withDescription("Delayed message trigger")
                .startAt(Date.from(LocalDateTime.now().plusMinutes(delayMinutes)
                        .atZone(ZoneId.systemDefault()).toInstant()))
                .build();
    }

}

