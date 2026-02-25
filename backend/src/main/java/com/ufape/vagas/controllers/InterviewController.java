package com.ufape.vagas.controllers;

import com.ufape.vagas.models.Interview;
import com.ufape.vagas.services.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interviews")
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    @PostMapping
    public Interview createInterview(@RequestBody Interview interview) {
        return interviewService.save(interview);
    }

    @GetMapping
    public List<Interview> getAllInterviews() {
        return interviewService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Interview> getInterviewById(@PathVariable Long id) {
        return interviewService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Interview> updateInterview(@PathVariable Long id, @RequestBody Interview details) {
        return interviewService.findById(id).map(interview -> {
            interview.setDateTime(details.getDateTime());
            interview.setLocation(details.getLocation());
            interview.setFeedback(details.getFeedback());
            interview.setStatus(details.getStatus());
            
            if (details.getApplication() != null) {
                interview.setApplication(details.getApplication());
            }
            
            Interview updated = interviewService.save(interview);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInterview(@PathVariable Long id) {
        if (interviewService.findById(id).isPresent()) {
            interviewService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}