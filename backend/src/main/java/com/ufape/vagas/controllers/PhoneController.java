package com.ufape.vagas.controllers;

import com.ufape.vagas.models.Phone;
import com.ufape.vagas.services.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/phones")
public class PhoneController {

    @Autowired
    private PhoneService phoneService;

    @PostMapping
    public Phone createPhone(@RequestBody Phone phone) {
        return phoneService.save(phone);
    }

    @GetMapping
    public List<Phone> getAllPhones() {
        return phoneService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Phone> getPhoneById(@PathVariable Long id) {
        return phoneService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Phone> updatePhone(@PathVariable Long id, @RequestBody Phone details) {
        return phoneService.findById(id).map(phone -> {
            phone.setNumber(details.getNumber());
            
            
            
            Phone updated = phoneService.save(phone);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhone(@PathVariable Long id) {
        if (phoneService.findById(id).isPresent()) {
            phoneService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}