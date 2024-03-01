package com.exercise_1.controller;

import com.exercise_1.model.AuditAction;
import com.exercise_1.service.AuditActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auditActions")
public class AuditActionController {

    private final AuditActionService auditActionService;

    @Autowired
    public AuditActionController(AuditActionService auditActionService) {
        this.auditActionService = auditActionService;
    }

    @GetMapping
    public ResponseEntity<List<AuditAction>> getAllAuditActions() {
        return ResponseEntity.ok(auditActionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditAction> getAuditActionById(@PathVariable Long id) {
        return auditActionService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AuditAction> createAuditAction(@RequestBody AuditAction auditAction) {
        AuditAction savedAuditAction = auditActionService.save(auditAction);
        return ResponseEntity.ok(savedAuditAction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuditAction> updateAuditAction(@PathVariable Long id, @RequestBody AuditAction auditActionDetails) {
        return auditActionService.findById(id)
                .map(existingAuditAction -> {
                    existingAuditAction.setUser(auditActionDetails.getUser());
                    existingAuditAction.setRequest(auditActionDetails.getRequest());
                    existingAuditAction.setActionDetail(auditActionDetails.getActionDetail());
                    existingAuditAction.setActionDate(auditActionDetails.getActionDate());
                    AuditAction updatedAuditAction = auditActionService.save(existingAuditAction);
                    return ResponseEntity.ok(updatedAuditAction);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuditAction(@PathVariable Long id) {
        if (auditActionService.findById(id).isPresent()) {
            auditActionService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}