package com.exercise_1.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tracking_code")
@Data
@NoArgsConstructor
public class TrackingCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tracking_code_id")
    private Long trackingCodeId;

    @Column(name = "code", nullable = false, length = 255)
    private String code;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "generation_date", nullable = false)
    private LocalDateTime generationDate;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @OneToOne
    @JoinColumn(name = "request_id")
    private Request request;
}