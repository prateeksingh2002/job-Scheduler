package com.jobscheduler1.service;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class JobExecutionJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // Job execution logic
    }
}
