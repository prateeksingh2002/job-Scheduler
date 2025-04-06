package com.jobscheduler1.util;

import com.jobscheduler1.model.entity.JobEntity;
import com.jobscheduler1.model.enums.ScheduleType;

import java.util.List;

public class CronExpressionGenerator {
    public static String generateCronExpression(JobEntity job) {
        if (job.getScheduleType() == ScheduleType.RECURRING) {
            // Parse job's schedule details to generate cron expression
            // This is a simplified version - you'd need to implement based on your scheduleDetails structure
            return "0 0 * * * ?"; // Example: hourly
        }
        throw new IllegalArgumentException("Unsupported schedule type for cron expression: " + job.getScheduleType());
    }
}