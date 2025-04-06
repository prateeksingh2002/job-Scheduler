package com.jobscheduler1.model.entity;

import com.jobscheduler1.model.enums.ExecutionStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "executions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExecutionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private JobEntity job;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private ExecutionStatus status;

    @Column(columnDefinition = "text")
    private String output;

    @Column(name = "error", columnDefinition = "text")
    private String error;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}