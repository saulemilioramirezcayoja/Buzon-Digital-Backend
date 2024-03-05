package com.exercise_1.service;

import com.exercise_1.model.TrackingCode;
import com.exercise_1.repository.TrackingCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

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

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random RANDOM = new Random();

    public String generateTrackingCode() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            int index = RANDOM.nextInt(ALPHABET.length());
            char randomChar = ALPHABET.charAt(index);
            builder.append(randomChar);
        }

        int randomNumber = 100000 + RANDOM.nextInt(900000);
        builder.append(randomNumber);

        int year = Year.now().getValue();
        builder.append(year);

        return builder.toString();
    }
}