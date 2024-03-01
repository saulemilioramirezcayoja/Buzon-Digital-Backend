package com.exercise_1.repository;

import com.exercise_1.model.RequestAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestAttachmentRepository extends JpaRepository<RequestAttachment, Long> {
}