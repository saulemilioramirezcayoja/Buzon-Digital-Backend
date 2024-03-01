package com.exercise_1.repository;

import com.exercise_1.model.AuditAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditActionRepository extends JpaRepository<AuditAction, Long> {
}