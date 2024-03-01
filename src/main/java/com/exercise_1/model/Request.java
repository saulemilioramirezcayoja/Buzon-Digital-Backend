package com.exercise_1.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "request")
@Data
@NoArgsConstructor
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "closed_date")
    private LocalDateTime closedDate;

    @Column(name = "tracking_code_id")
    private Long trackingCodeId;

    @Column(name = "comment")
    private String comment;

    @Column(name = "applicant_name", length = 255)
    private String applicantName;

    @Column(name = "applicant_id", length = 50)
    private String applicantId;

    @Column(name = "applicant_email", length = 255)
    private String applicantEmail;

    @Column(name = "applicant_phone", length = 20)
    private String applicantPhone;

    @Column(name = "additional_requirements", columnDefinition = "TEXT")
    private String additionalRequirements;

    public enum State {
        received, in_process, completed
    }
}