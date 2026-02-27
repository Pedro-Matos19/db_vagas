package com.ufape.vagas.controllers;

import com.ufape.vagas.dto.CandidateRequest;
import com.ufape.vagas.models.Candidate;
import com.ufape.vagas.services.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        if (candidateService.findById(id).isPresent()) {
            candidateService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
