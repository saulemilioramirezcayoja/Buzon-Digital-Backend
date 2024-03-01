package com.exercise_1.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_action")
@Data
@NoArgsConstructor
public class AuditAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audit_action_id")
    private Long auditActionId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "request_id", nullable = false)
    private Request request;

    @Column(name = "action_detail", nullable = false, columnDefinition = "TEXT")
    private String actionDetail;

    @Column(name = "action_date", nullable = false)
    private LocalDateTime actionDate;
}