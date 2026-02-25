package com.ufape.vagas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufape.vagas.dto.JobRequest;
import com.ufape.vagas.models.Company;
import com.ufape.vagas.models.Job;
import com.ufape.vagas.models.Skill;
import com.ufape.vagas.repositories.CompanyRepository;
import com.ufape.vagas.repositories.JobRepository;
import com.ufape.vagas.repositories.SkillRepository;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;
    
    @Autowired
    private CompanyRepository companyRepository;
    
    @Autowired
    private SkillRepository skillRepository;

    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    public Optional<Job> findById(Long id) {
        return jobRepository.findById(id);
    }

    public Job save(Job job) {
        return jobRepository.save(job);
    }
    
    public Job create(JobRequest jobRequest) {
    	Company company = companyRepository.findById(jobRequest.companyId()).orElseThrow(() -> new RuntimeException("Id of company not found"));
    	
    	List<Skill> skills = skillRepository.findAllById(jobRequest.skillsId());
    	
    	Job job = new Job(jobRequest.title(), jobRequest.description(), jobRequest.type(), jobRequest.salary(), 
    					  jobRequest.status(), company, skills);
    	
    	
        return jobRepository.save(job);
    }

    public void deleteById(Long id) {
        jobRepository.deleteById(id);
    }
}