package com.jobscheduler1.config;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulerConfig {
    @Bean
    public Scheduler scheduler() throws SchedulerException {
        return StdSchedulerFactory.getDefaultScheduler();
    }
}