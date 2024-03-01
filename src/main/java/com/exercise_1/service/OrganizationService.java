package com.exercise_1.service;

import com.exercise_1.model.Organization;
import com.exercise_1.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public List<Organization> findAll() {
        return organizationRepository.findAll();
    }

    public Optional<Organization> findById(Long id) {
        return organizationRepository.findById(id);
    }

    public Organization save(Organization organization) {
        return organizationRepository.save(organization);
    }

    public void deleteById(Long id) {
        organizationRepository.deleteById(id);
    }
}