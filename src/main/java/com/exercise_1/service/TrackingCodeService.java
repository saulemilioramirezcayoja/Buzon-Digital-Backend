package com.exercise_1.service;

import com.exercise_1.model.TrackingCode;
import com.exercise_1.repository.TrackingCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrackingCodeService {

    private final TrackingCodeRepository trackingCodeRepository;

    @Autowired
    public TrackingCodeService(TrackingCodeRepository trackingCodeRepository) {
        this.trackingCodeRepository = trackingCodeRepository;
    }

    public List<TrackingCode> findAll() {
        return trackingCodeRepository.findAll();
    }

    public Optional<TrackingCode> findById(Long id) {
        return trackingCodeRepository.findById(id);
    }

    public TrackingCode save(TrackingCode trackingCode) {
        return trackingCodeRepository.save(trackingCode);
    }

    public void deleteById(Long id) {
        trackingCodeRepository.deleteById(id);
    }
}