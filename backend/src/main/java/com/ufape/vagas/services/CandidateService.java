package com.ufape.vagas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ufape.vagas.dto.CandidateRequest;
import com.ufape.vagas.enums.ApplicationStatus;
import com.ufape.vagas.enums.InterviewStatus;
import com.ufape.vagas.enums.UserStatus;
import com.ufape.vagas.exceptions.IdNotFoundException;
import com.ufape.vagas.models.Application;
import com.ufape.vagas.models.Candidate;
import com.ufape.vagas.models.Course;
import com.ufape.vagas.models.Interview;
import com.ufape.vagas.models.Skill;
import com.ufape.vagas.models.User;
import com.ufape.vagas.repositories.ApplicationRepository;
import com.ufape.vagas.repositories.CandidateRepository;
import com.ufape.vagas.repositories.CourseRepository;
import com.ufape.vagas.repositories.InterviewRepository;
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
	public void disableCandidate(Long id) {
		Optional<Candidate> candidateOpt = candidateRepository.findById(id);

		Candidate candidate = candidateOpt.orElseThrow(() -> new IdNotFoundException());

		User user = candidate.getUser();
		user.setStatus(UserStatus.INATIVO);

		List<Application> applications = applicationRepository.findByCandidateId(id);

		for (Application a : applications) {
			a.setStatus(ApplicationStatus.CANCELADA);
			List<Interview> interviews = interviewRepository.findByApplicationId(a.getId());
			interviews.forEach(i -> {
				if (i.getStatus() != InterviewStatus.REALIZADA)
					i.setStatus(InterviewStatus.CANCELADA);
			});
			a.setInterviews(interviews);
		}

		candidateRepository.save(candidate);

	}
	
	@Transactional
	public void enableCandidate(Long id) {
		Optional<Candidate> candidateOpt = candidateRepository.findById(id);

		Candidate candidate = candidateOpt.orElseThrow(() -> new IdNotFoundException());

		User user = candidate.getUser();
		user.setStatus(UserStatus.ATIVO);


		candidateRepository.save(candidate);
	}

	
}
