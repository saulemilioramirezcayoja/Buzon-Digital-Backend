package com.exercise_1.service;

import com.exercise_1.model.AuditAction;
import com.exercise_1.repository.AuditActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuditActionService {

    private final AuditActionRepository auditActionRepository;

    @Autowired
    public AuditActionService(AuditActionRepository auditActionRepository) {
        this.auditActionRepository = auditActionRepository;
    }

    public List<AuditAction> findAll() {
        return auditActionRepository.findAll();
    }

    public Optional<AuditAction> findById(Long id) {
        return auditActionRepository.findById(id);
    }

    public AuditAction save(AuditAction auditAction) {
        return auditActionRepository.save(auditAction);
    }

    public void deleteById(Long id) {
        auditActionRepository.deleteById(id);
    }
}