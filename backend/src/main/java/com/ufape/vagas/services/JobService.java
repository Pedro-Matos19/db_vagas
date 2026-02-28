package com.ufape.vagas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ufape.vagas.dto.JobRequest;
import com.ufape.vagas.models.Application;
import com.ufape.vagas.models.Company;
import com.ufape.vagas.models.Interview;
import com.ufape.vagas.models.Job;
import com.ufape.vagas.models.JobPerformanceSummary;
import com.ufape.vagas.models.Skill;
import com.ufape.vagas.repositories.ApplicationRepository;
import com.ufape.vagas.repositories.CompanyRepository;
import com.ufape.vagas.repositories.InterviewRepository;
import com.ufape.vagas.repositories.JobPerformanceSummaryRepository;
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

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private JobPerformanceSummaryRepository jobPerformanceSummaryRepository;

    public List<JobPerformanceSummary> findTop5JobPerformanceSummaries() {
        return jobPerformanceSummaryRepository.findTop5Details();
    }

    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    public Optional<Job> findById(Long id) {
        return jobRepository.findById(id);
    }

    @Transactional
    public Job save(Job job) {
        return jobRepository.save(job);
    }
    
    @Transactional
    public Job create(JobRequest jobRequest) {
    	Company company = companyRepository.findById(jobRequest.companyId()).orElseThrow(() -> new RuntimeException("Id of company not found"));
    	
    	List<Skill> skills = skillRepository.findAllById(jobRequest.skillsId());
    	
    	Job job = new Job(jobRequest.title(), jobRequest.description(), jobRequest.type(), jobRequest.salary(), 
    					  jobRequest.status(), company, skills);
    	
    	
        return jobRepository.save(job);
    }

    @Transactional
    public Job update(Long id, JobRequest jobRequest) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        job.setTitle(jobRequest.title());
        job.setDescription(jobRequest.description());
        job.setType(jobRequest.type());
        job.setSalary(jobRequest.salary());
        job.setStatus(jobRequest.status());

        if (jobRequest.skillsId() != null) {
            List<Skill> skills = skillRepository.findAllById(jobRequest.skillsId());
            job.setRequiredSkills(skills);
        }

        if (jobRequest.companyId() != null) {
             Company company = companyRepository.findById(jobRequest.companyId())
                    .orElseThrow(() -> new RuntimeException("Company not found"));
             job.setCompany(company);
        }

        return jobRepository.save(job);
    }

    @Transactional
    public void deleteById(Long id) {
        Optional<Job> jobOpt = jobRepository.findById(id);
        if (jobOpt.isPresent()) {
            Job job = jobOpt.get();

            job.getRequiredSkills().clear();
            jobRepository.save(job);

            List<Application> applications = applicationRepository.findByJobId(id);
            for (Application app : applications) {
                List<Interview> interviews = interviewRepository.findByApplicationId(app.getId());
                interviewRepository.deleteAll(interviews);
                applicationRepository.delete(app);
            }

            jobRepository.deleteById(id);
        }
    }
}
