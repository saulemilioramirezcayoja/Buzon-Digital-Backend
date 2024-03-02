package com.exercise_1.service;

import com.exercise_1.model.Request;
import com.exercise_1.model.User;
import com.exercise_1.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestService {

    private final RequestRepository requestRepository;

    @Autowired
    private UserService userService;

    @Autowired
    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
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
        return requestRepository.save(request);
    }

    public void deleteById(Long id) {
        requestRepository.deleteById(id);
    }
}