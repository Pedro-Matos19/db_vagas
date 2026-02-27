package com.ufape.vagas.controllers;

import com.ufape.vagas.models.Curriculum;
import com.ufape.vagas.services.CurriculumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/curriculums")
public class CurriculumController {

    @Autowired
    private CurriculumService curriculumService;

    @PostMapping
    public Curriculum createCurriculum(@RequestBody Curriculum curriculum) {
        return curriculumService.save(curriculum);
    }

    @GetMapping
    public List<Curriculum> getAllCurriculums() {
        return curriculumService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curriculum> getCurriculumById(@PathVariable Long id) {
        return curriculumService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curriculum> updateCurriculum(@PathVariable Long id, @RequestBody Curriculum details) {
        return curriculumService.findById(id).map(curriculum -> {
            curriculum.setObjective(details.getObjective());
            curriculum.setExperience(details.getExperience());
            curriculum.setPortfolioLink(details.getPortfolioLink());
            
            
            
            Curriculum updated = curriculumService.save(curriculum);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurriculum(@PathVariable Long id) {
        if (curriculumService.findById(id).isPresent()) {
            curriculumService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}