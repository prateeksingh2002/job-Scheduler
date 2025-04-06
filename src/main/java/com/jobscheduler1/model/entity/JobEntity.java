package com.jobscheduler1.model.entity;

import com.jobscheduler1.model.enums.JobStatus;
import com.jobscheduler1.model.enums.ScheduleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Entity
@Table(name = "jobs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private String command;

    @Column(name = "binary_ref")
    private String binaryRef;

    @Enumerated(EnumType.STRING)
    @Column(name = "schedule_type", nullable = false)
    private ScheduleType scheduleType;

    @Column(name = "schedule_details", columnDefinition = "jsonb")
    @Future(message = "Scheduled time must be in the future")
    private String scheduleDetails;

    @Enumerated(EnumType.STRING)
    private JobStatus status;

    @Column(name = "scheduled_time")
    private LocalDateTime scheduledTime;

    @Column(name = "time_zone")
    private String timeZone;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}