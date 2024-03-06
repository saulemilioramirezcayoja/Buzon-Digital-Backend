package com.exercise_1.controller;

import com.exercise_1.model.Organization;
import com.exercise_1.service.OrganizationService;
import com.exercise_1.util.OrganizationIdCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping
    public ResponseEntity<List<Organization>> getAllOrganizations() {
        return ResponseEntity.ok(organizationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Organization> getOrganizationById(@PathVariable Long id) {
        return organizationService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/encode-id")
    public ResponseEntity<?> encodeOrganizationId(@RequestParam("orgId") Long orgId) {
        Optional<Organization> organization = organizationService.findById(orgId);
        if (!organization.isPresent()) {
            return ResponseEntity.badRequest().body("Organization ID does not exist");
        }

        try {
            String encodedId = OrganizationIdCodec.encode(orgId);
            return ResponseEntity.ok(encodedId);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error encoding organization ID: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Organization> createOrganization(@RequestBody Organization organization) {
        Organization savedOrganization = organizationService.save(organization);
        return ResponseEntity.ok(savedOrganization);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Organization> updateOrganization(@PathVariable Long id, @RequestBody Organization organizationDetails) {
        return organizationService.findById(id)
                .map(organization -> {
                    organization.setName(organizationDetails.getName());
                    organization.setEmail(organizationDetails.getEmail());
                    organization.setPhone(organizationDetails.getPhone());
                    organization.setAddress(organizationDetails.getAddress());
                    organization.setStatus(organizationDetails.getStatus());
                    organization.setAdditionalInfo(organizationDetails.getAdditionalInfo());
                    Organization updatedOrganization = organizationService.save(organization);
                    return ResponseEntity.ok(updatedOrganization);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable Long id) {
        if (organizationService.findById(id).isPresent()) {
            organizationService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}