package com.exercise_1.controller;

import com.exercise_1.model.TrackingCode;
import com.exercise_1.service.TrackingCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trackingCodes")
public class TrackingCodeController {

    private final TrackingCodeService trackingCodeService;

    @Autowired
    public TrackingCodeController(TrackingCodeService trackingCodeService) {
        this.trackingCodeService = trackingCodeService;
    }

    @GetMapping
    public ResponseEntity<List<TrackingCode>> getAllTrackingCodes() {
        return ResponseEntity.ok(trackingCodeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrackingCode> getTrackingCodeById(@PathVariable Long id) {
        return trackingCodeService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TrackingCode> createTrackingCode(@RequestBody TrackingCode trackingCode) {
        TrackingCode savedTrackingCode = trackingCodeService.save(trackingCode);
        return ResponseEntity.ok(savedTrackingCode);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrackingCode> updateTrackingCode(@PathVariable Long id, @RequestBody TrackingCode trackingCodeDetails) {
        return trackingCodeService.findById(id)
                .map(existingTrackingCode -> {
                    existingTrackingCode.setCode(trackingCodeDetails.getCode());
                    existingTrackingCode.setIsActive(trackingCodeDetails.getIsActive()); // Corregido aquí
                    existingTrackingCode.setGenerationDate(trackingCodeDetails.getGenerationDate());
                    existingTrackingCode.setExpiresAt(trackingCodeDetails.getExpiresAt());
                    existingTrackingCode.setRequest(trackingCodeDetails.getRequest());
                    TrackingCode updatedTrackingCode = trackingCodeService.save(existingTrackingCode);
                    return ResponseEntity.ok(updatedTrackingCode);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrackingCode(@PathVariable Long id) {
        if (trackingCodeService.findById(id).isPresent()) {
            trackingCodeService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}