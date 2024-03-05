package com.exercise_1.service;

import com.exercise_1.model.Request;
import com.exercise_1.model.TrackingCode;
import com.exercise_1.model.User;
import com.exercise_1.repository.RequestRepository;
import com.exercise_1.repository.TrackingCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RequestService {

    private final RequestRepository requestRepository;
    private final TrackingCodeRepository trackingCodeRepository;

    @Autowired
    private TrackingCodeService trackingCodeService;

    @Autowired
    private UserService userService;

    @Autowired
    public RequestService(RequestRepository requestRepository, TrackingCodeService trackingCodeService, TrackingCodeRepository trackingCodeRepository) {
        this.requestRepository = requestRepository;
        this.trackingCodeService = trackingCodeService;
        this.trackingCodeRepository = trackingCodeRepository;
    }

    public List<Request> findRequestsByAuthenticatedUserOrganization() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userService.findByEmail(email).orElse(null);

        if (user != null && user.getOrganization() != null) {
            return requestRepository.findByOrganizationOrganizationId(user.getOrganization().getOrganizationId());
        } else {
            return List.of();
        }
    }

    public List<Request> findAll() {
        return requestRepository.findAll();
    }

    public Optional<Request> findById(Long id) {
        return requestRepository.findById(id);
    }

    public Request save(Request request) {
        Request savedRequest = requestRepository.save(request);

        String code = trackingCodeService.generateTrackingCode();
        TrackingCode trackingCode = new TrackingCode();
        trackingCode.setCode(code);
        trackingCode.setIsActive(true);
        trackingCode.setGenerationDate(LocalDateTime.now());
        trackingCode.setExpiresAt(LocalDateTime.now().plusDays(30));
        trackingCode.setRequest(savedRequest);

        TrackingCode savedTrackingCode = trackingCodeRepository.save(trackingCode);

        savedRequest.setTrackingCodeId(savedTrackingCode.getTrackingCodeId());

        return requestRepository.save(savedRequest);
    }

    public void deleteById(Long id) {
        requestRepository.deleteById(id);
    }
}
