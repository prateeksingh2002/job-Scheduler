package com.jobscheduler1.repository;

import com.jobscheduler1.model.entity.JobEntity;
import com.jobscheduler1.model.enums.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, UUID> {
    List<JobEntity> findByStatus(JobStatus status);

    @Query("SELECT j FROM JobEntity j WHERE j.scheduleType = 'SPECIFIC_TIME' AND j.status = 'SCHEDULED' AND j.scheduleDetails LIKE %:dateTime%")
    List<JobEntity> findJobsToExecuteAtSpecificTime(String dateTime);
}