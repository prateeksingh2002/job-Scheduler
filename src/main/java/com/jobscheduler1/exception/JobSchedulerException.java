package com.jobscheduler1.exception;

public class JobSchedulerException extends RuntimeException {
    public JobSchedulerException(String message) {
        super(message);
    }

    public JobSchedulerException(String message, Throwable cause) {
        super(message, cause);
    }
}