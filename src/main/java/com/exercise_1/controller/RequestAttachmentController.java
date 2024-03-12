package com.exercise_1.controller;

import com.exercise_1.model.RequestAttachment;
import com.exercise_1.service.RequestAttachmentService;
import com.exercise_1.dto.FileUploadDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/requestAttachments")
public class RequestAttachmentController {

    private final RequestAttachmentService requestAttachmentService;

    @Autowired
    public RequestAttachmentController(RequestAttachmentService requestAttachmentService) {
        this.requestAttachmentService = requestAttachmentService;
    }

    @GetMapping
    public ResponseEntity<List<RequestAttachment>> getAllRequestAttachments() {
        return ResponseEntity.ok(requestAttachmentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequestAttachment> getRequestAttachmentById(@PathVariable Long id) {
        return requestAttachmentService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RequestAttachment> createRequestAttachment(@RequestBody RequestAttachment requestAttachment) {
        RequestAttachment savedRequestAttachment = requestAttachmentService.save(requestAttachment);
        return ResponseEntity.ok(savedRequestAttachment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RequestAttachment> updateRequestAttachment(@PathVariable Long id, @RequestBody RequestAttachment requestAttachmentDetails) {
        return requestAttachmentService.findById(id)
                .map(existingAttachment -> {
                    existingAttachment.setRequest(requestAttachmentDetails.getRequest());
                    existingAttachment.setFileName(requestAttachmentDetails.getFileName());
                    existingAttachment.setFilePath(requestAttachmentDetails.getFilePath());
                    existingAttachment.setFileType(requestAttachmentDetails.getFileType());
                    existingAttachment.setAttachmentType(requestAttachmentDetails.getAttachmentType());
                    existingAttachment.setUploadedAt(requestAttachmentDetails.getUploadedAt());
                    existingAttachment.setFileSize(requestAttachmentDetails.getFileSize());
                    RequestAttachment updatedAttachment = requestAttachmentService.save(existingAttachment);
                    return ResponseEntity.ok(updatedAttachment);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequestAttachment(@PathVariable Long id) {
        if (requestAttachmentService.findById(id).isPresent()) {
            requestAttachmentService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFiles(@RequestBody List<FileUploadDTO> fileUploadDTOList) {
        try {
            List<RequestAttachment> attachments = requestAttachmentService.processMultipleFileUploads(fileUploadDTOList);
            return ResponseEntity.ok(attachments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading files: " + e.getMessage());
        }
    }
}