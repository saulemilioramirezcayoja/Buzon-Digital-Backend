package com.exercise_1.repository;

import com.exercise_1.model.TrackingCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrackingCodeRepository extends JpaRepository<TrackingCode, Long> {
    Optional<TrackingCode> findByCode(String code);
}