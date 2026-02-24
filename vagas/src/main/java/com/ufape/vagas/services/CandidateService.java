package com.ufape.vagas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufape.vagas.dto.CandidateRequest;
import com.ufape.vagas.models.Candidate;
import com.ufape.vagas.models.Course;
import com.ufape.vagas.models.Skill;
import com.ufape.vagas.models.User;
import com.ufape.vagas.repositories.CandidateRepository;
import com.ufape.vagas.repositories.CourseRepository;
import com.ufape.vagas.repositories.SkillRepository;
import com.ufape.vagas.repositories.UserRepository;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;
    
    @Autowired
    private SkillRepository skillRepository;
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private UserRepository userRepository;

    public List<Candidate> findAll() {
        return candidateRepository.findAll();
    }

    public Optional<Candidate> findById(Long id) {
        return candidateRepository.findById(id);
    }

    public Candidate save(Candidate candidate) {
        return candidateRepository.save(candidate);
    }
    
    public Candidate create(CandidateRequest candidateRequest) {
    	List<Skill> skills = skillRepository.findAllById(candidateRequest.skillsId());
    	
    	List<Course> courses = courseRepository.findAllById(candidateRequest.coursesId());
    	
    	User user = userRepository.findById(candidateRequest.userId()).orElseThrow(() -> new RuntimeException("Id of user not found"));
    	
    	Candidate candidate = new Candidate(candidateRequest.cpf(), candidateRequest.name(),  candidateRequest.birthDate(), user, skills, courses);
    	
        return candidateRepository.save(candidate);
    }
    
    public Candidate update(CandidateRequest candidateRequest, Long id) {
    	Candidate candidate = candidateRepository.findById(id).orElseThrow(() -> new RuntimeException("Id of candidate not found"));
    	
    	List<Skill> skills = skillRepository.findAllById(candidateRequest.skillsId());
    	
    	List<Course> courses = courseRepository.findAllById(candidateRequest.coursesId());
    	
    	candidate.setName(candidateRequest.name());
    	candidate.setCpf(candidateRequest.cpf());
    	candidate.setBirthDate(candidateRequest.birthDate());
    	candidate.setSkills(skills);
    	candidate.setCourses(courses);
    	
        return candidateRepository.save(candidate);
    }

    public void deleteById(Long id) {
        candidateRepository.deleteById(id);
    }
}