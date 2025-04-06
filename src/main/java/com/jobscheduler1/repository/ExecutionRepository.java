package com.jobscheduler1.repository;

import com.jobscheduler1.model.entity.ExecutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExecutionRepository extends JpaRepository<ExecutionEntity, UUID> {
    List<ExecutionEntity> findByJobId(UUID jobId);
    List<ExecutionEntity> findByJobIdOrderByStartTimeDesc(UUID jobId);
}