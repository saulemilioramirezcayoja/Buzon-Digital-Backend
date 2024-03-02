package com.exercise_1.repository;

import com.exercise_1.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByOrganizationOrganizationId(Long organizationId);
}