package com.jobscheduler1.service;

import com.jobscheduler1.kafka.dto.CallbackMessage;
import com.jobscheduler1.kafka.producer.CallbackProducer;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class CallbackJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        CallbackMessage message = (CallbackMessage) context.getJobDetail()
                .getJobDataMap().get("message");

        CallbackProducer callbackProducer = (CallbackProducer) context.getJobDetail()
                .getJobDataMap().get("callbackProducer");

        callbackProducer.sendCallbackMessage(message);
    }
}