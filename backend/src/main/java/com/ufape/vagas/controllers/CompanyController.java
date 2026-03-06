package com.ufape.vagas.controllers;

import com.ufape.vagas.models.Company;
import com.ufape.vagas.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping
    public Company createCompany(@RequestBody Company company) {
        return companyService.save(company);
    }

    @GetMapping
    public List<Company> getAllCompanies() {
        return companyService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        return companyService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @RequestBody Company details) {
        return companyService.findById(id).map(company -> {
            company.setCnpj(details.getCnpj());
            company.setCorporateName(details.getCorporateName());
            company.setLocation(details.getLocation());
            company.setWebsite(details.getWebsite());
            company.setSector(details.getSector());
            company.setDescription(details.getDescription());

            Company updated = companyService.save(company);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> disableCompany(@PathVariable Long id) {
    	companyService.disableCompany(id);
    	return ResponseEntity.status(HttpStatus.OK).build();
    }
    
    @PutMapping("/unable/{id}")
    public ResponseEntity<Void> unableCompany(@PathVariable Long id) {
    	companyService.unableCompany(id);
    	return ResponseEntity.status(HttpStatus.OK).build();
    }
}
