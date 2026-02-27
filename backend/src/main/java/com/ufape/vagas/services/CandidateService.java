package com.ufape.vagas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ufape.vagas.dto.CandidateRequest;
import com.ufape.vagas.models.Application;
import com.ufape.vagas.models.Candidate;
import com.ufape.vagas.models.Course;
import com.ufape.vagas.models.Interview;
import com.ufape.vagas.models.Skill;
import com.ufape.vagas.models.User;
import com.ufape.vagas.repositories.ApplicationRepository;
import com.ufape.vagas.repositories.CandidateRepository;
import com.ufape.vagas.repositories.CourseRepository;
import com.ufape.vagas.repositories.CurriculumRepository;
import com.ufape.vagas.repositories.InterviewRepository;
import com.ufape.vagas.repositories.PhoneRepository;
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

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private CurriculumRepository curriculumRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private InterviewRepository interviewRepository;

    public List<Candidate> findAll() {
        return candidateRepository.findAll();
    }

    public Optional<Candidate> findById(Long id) {
        return candidateRepository.findById(id);
    }

    @Transactional
    public Candidate save(Candidate candidate) {
        return candidateRepository.save(candidate);
    }
    
    @Transactional
    public Candidate create(CandidateRequest candidateRequest) {
        List<Skill> skills = (candidateRequest.skillsId() != null) ? 
            skillRepository.findAllById(candidateRequest.skillsId()) : List.of();
        
        List<Course> courses = (candidateRequest.coursesId() != null) ? 
            courseRepository.findAllById(candidateRequest.coursesId()) : List.of();
        
        String cpf = candidateRequest.cpf().replaceAll("\\D", "");

        if (candidateRepository.findByCpf(cpf).isPresent()) {
            throw new RuntimeException("Já existe um candidato cadastrado com este CPF.");
        }
        
        User user;
        if (candidateRequest.userId() != null && candidateRequest.userId() > 0) {
            user = userRepository.findById(candidateRequest.userId())
                .orElseThrow(() -> new RuntimeException("Id of user not found"));
        } else {
            User newUser = new User();
             String email = cpf + "@candidate.com";
             if (userRepository.findByEmail(email).isPresent()) {
                 throw new RuntimeException("Já existe um usuário cadastrado com este CPF (email: " + email + ").");
             }
             newUser.setEmail(email);
             newUser.setPassword(cpf);
             
             user = userRepository.save(newUser);
        }
        
        Candidate candidate = new Candidate(cpf, candidateRequest.name(),  candidateRequest.birthDate(), user, skills, courses);
        
        return candidateRepository.save(candidate);
    }
    
    @Transactional
    public Candidate update(CandidateRequest candidateRequest, Long id) {
    	Candidate candidate = candidateRepository.findById(id).orElseThrow(() -> new RuntimeException("Id of candidate not found"));
    	
    	List<Skill> skills = (candidateRequest.skillsId() != null) ? 
            skillRepository.findAllById(candidateRequest.skillsId()) : List.of();
    	
    	List<Course> courses = (candidateRequest.coursesId() != null) ? 
            courseRepository.findAllById(candidateRequest.coursesId()) : List.of();
    	
    	candidate.setName(candidateRequest.name());
    	candidate.setCpf(candidateRequest.cpf().replaceAll("\\D", ""));
    	candidate.setBirthDate(candidateRequest.birthDate());
    	candidate.setSkills(skills);
    	candidate.setCourses(courses);
    	
        return candidateRepository.save(candidate);
    }

    @Transactional
    public void deleteById(Long id) {
        Optional<Candidate> candidateOpt = candidateRepository.findById(id);
        if (candidateOpt.isPresent()) {
            Candidate candidate = candidateOpt.get();

            phoneRepository.findByCandidateId(id).forEach(phone -> phoneRepository.delete(phone));

            curriculumRepository.findByCandidateId(id).ifPresent(curriculum -> curriculumRepository.delete(curriculum));

            List<Application> applications = applicationRepository.findByCandidateId(id);
            for (Application app : applications) {
                List<Interview> interviews = interviewRepository.findByApplicationId(app.getId());
                interviewRepository.deleteAll(interviews);
                applicationRepository.delete(app);
            }

            candidate.getSkills().clear();
            candidate.getCourses().clear();
            candidateRepository.save(candidate);

            candidateRepository.deleteById(id);

            userRepository.deleteById(id);
        }
    }
}
