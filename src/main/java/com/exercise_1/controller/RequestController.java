package com.exercise_1.controller;

import com.exercise_1.model.Organization;
import com.exercise_1.model.Request;
import com.exercise_1.service.OrganizationService;
import com.exercise_1.service.RequestService;
import com.exercise_1.util.OrganizationIdCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    private final RequestService requestService;
    private final OrganizationService organizationService;

    @Autowired
    public RequestController(RequestService requestService, OrganizationService organizationService) {
        this.requestService = requestService;
        this.organizationService = organizationService;
    }

    @GetMapping
    public ResponseEntity<List<Request>> getAllRequests() {
        return ResponseEntity.ok(requestService.findAll());
    }

    @GetMapping("/by-organization")
    public ResponseEntity<List<Request>> getRequestsByAuthenticatedUserOrganization() {
        List<Request> requests = requestService.findRequestsByAuthenticatedUserOrganization();
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Request> getRequestById(@PathVariable Long id) {
        return requestService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<Request> createRequest(@RequestParam("org") String encodedOrgId, @RequestBody Request request) {
        Long organizationId = OrganizationIdCodec.decode(encodedOrgId);

        Organization organization = organizationService.findById(organizationId)
                .orElseThrow(() -> new RuntimeException("Organization not found for ID: " + organizationId));
        request.setOrganization(organization);

        Request savedRequest = requestService.save(request);
        return ResponseEntity.ok(savedRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Request> updateRequest(@PathVariable Long id, @RequestBody Request requestDetails) {
        return requestService.findById(id)
                .map(existingRequest -> {
                    existingRequest.setOrganization(requestDetails.getOrganization());
                    existingRequest.setState(requestDetails.getState());
                    existingRequest.setCreationDate(requestDetails.getCreationDate());
                    existingRequest.setUpdateDate(requestDetails.getUpdateDate());
                    existingRequest.setClosedDate(requestDetails.getClosedDate());
                    existingRequest.setTrackingCodeId(requestDetails.getTrackingCodeId());
                    existingRequest.setComment(requestDetails.getComment());
                    existingRequest.setFirstName(requestDetails.getFirstName());
                    existingRequest.setLastName(requestDetails.getLastName());
                    existingRequest.setEmail(requestDetails.getEmail());
                    existingRequest.setPhoneNumber(requestDetails.getPhoneNumber());
                    existingRequest.setAddress(requestDetails.getAddress());
                    existingRequest.setOrganizationName(requestDetails.getOrganizationName());

                    Request updatedRequest = requestService.save(existingRequest);
                    return ResponseEntity.ok(updatedRequest);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        if (requestService.findById(id).isPresent()) {
            requestService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}