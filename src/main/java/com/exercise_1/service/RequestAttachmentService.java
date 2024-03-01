package com.exercise_1.service;

import com.exercise_1.model.RequestAttachment;
import com.exercise_1.repository.RequestAttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestAttachmentService {

    private final RequestAttachmentRepository requestAttachmentRepository;

    @Autowired
    public RequestAttachmentService(RequestAttachmentRepository requestAttachmentRepository) {
        this.requestAttachmentRepository = requestAttachmentRepository;
    }

    public List<RequestAttachment> findAll() {
        return requestAttachmentRepository.findAll();
    }

    public Optional<RequestAttachment> findById(Long id) {
        return requestAttachmentRepository.findById(id);
    }

    public RequestAttachment save(RequestAttachment requestAttachment) {
        return requestAttachmentRepository.save(requestAttachment);
    }

    public void deleteById(Long id) {
        requestAttachmentRepository.deleteById(id);
    }
}