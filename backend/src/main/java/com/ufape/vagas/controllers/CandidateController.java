package com.ufape.vagas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufape.vagas.dto.CandidateRequest;
import com.ufape.vagas.models.Candidate;
import com.ufape.vagas.services.CandidateService;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @PostMapping
    public Candidate createCandidate(@RequestBody CandidateRequest candidateRequest) {
        return candidateService.create(candidateRequest);
    }

    @GetMapping
    public List<Candidate> getAllCandidates() {
        return candidateService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidate> getCandidateById(@PathVariable Long id) {
        return candidateService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Candidate> updateCandidate(@PathVariable Long id, @RequestBody Candidate details) {
        return candidateService.findById(id).map(candidate -> {
            candidate.setName(details.getName());
            candidate.setCpf(details.getCpf());
            candidate.setBirthDate(details.getBirthDate());
            
            Candidate updated = candidateService.save(candidate);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCandidate(@PathVariable Long id) {
        candidateService.disableCandidate(id);
        
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
    @PutMapping("/enable/{id}")
    public ResponseEntity<?> unableCandidate(@PathVariable Long id) {
        candidateService.enableCandidate(id);
        
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

