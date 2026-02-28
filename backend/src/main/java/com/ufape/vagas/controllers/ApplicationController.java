package com.ufape.vagas.controllers;

import com.ufape.vagas.models.Application;
import com.ufape.vagas.models.DetailsApplication;
import com.ufape.vagas.services.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping
    public Application createApplication(@RequestBody Application application) {
        return applicationService.save(application);
    }

    @GetMapping
    public List<Application> getAllApplications() {
        return applicationService.findAll();
    }

    @GetMapping("/details")
    public List<DetailsApplication> getDetailsApplications() {
        return applicationService.findTop5DetailsApplications();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplicationById(@PathVariable Long id) {
        return applicationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Application> updateApplication(@PathVariable Long id, @RequestBody Application details) {
        return applicationService.findById(id).map(application -> {
            application.setStatus(details.getStatus());

            if (details.getCandidate() != null) {
                application.setCandidate(details.getCandidate());
            }
            if (details.getJob() != null) {
                application.setJob(details.getJob());
            }

            Application updated = applicationService.save(application);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        if (applicationService.findById(id).isPresent()) {
            applicationService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}