package com.ufape.vagas.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ufape.vagas.models.Application;
import com.ufape.vagas.models.Company;
import com.ufape.vagas.models.Interview;
import com.ufape.vagas.models.Job;
import com.ufape.vagas.models.User;
import com.ufape.vagas.repositories.ApplicationRepository;
import com.ufape.vagas.repositories.CompanyRepository;
import com.ufape.vagas.repositories.InterviewRepository;
import com.ufape.vagas.repositories.JobRepository;
import com.ufape.vagas.repositories.UserRepository;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private InterviewRepository interviewRepository;

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Optional<Company> findById(Long id) {
        return companyRepository.findById(id);
    }

    @Transactional
    public Company save(Company company) {
        if (company.getCnpj() != null) {
            company.setCnpj(company.getCnpj().replaceAll("\\D", ""));
        }

        if (company.getId() == null && companyRepository.findByCnpj(company.getCnpj()).isPresent()) {
             throw new RuntimeException("Já existe uma empresa cadastrada com este CNPJ.");
        }

        if (company.getId() == null) {
            User newUser = new User();
            String cnpjNumbers = company.getCnpj();
            String email = cnpjNumbers + "@company.com";
            
            if (userRepository.findByEmail(email).isPresent()) {
                throw new RuntimeException("Já existe um usuário cadastrado com este CNPJ (email: " + email + ").");
            }
            
            newUser.setEmail(email);
            newUser.setPassword(cnpjNumbers);
            User savedUser = userRepository.save(newUser);
            company.setUser(savedUser);
        } else if (company.getUser() != null && company.getUser().getId() != null) {
            User managedUser = userRepository.findById(company.getUser().getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            company.setUser(managedUser);
        }

        return companyRepository.save(company);
    }

    @Transactional
    public void deleteById(Long id) {
        Optional<Company> companyOpt = companyRepository.findById(id);
        if (companyOpt.isPresent()) {
            Company company = companyOpt.get();

            List<Job> jobs = jobRepository.findByCompanyId(id);
            for (Job job : jobs) {
                job.getRequiredSkills().clear();
                jobRepository.save(job);

                List<Application> applications = applicationRepository.findByJobId(job.getId());
                for (Application app : applications) {
                    List<Interview> interviews = interviewRepository.findByApplicationId(app.getId());
                    interviewRepository.deleteAll(interviews);
                    applicationRepository.delete(app);
                }
                jobRepository.delete(job);
            }

            companyRepository.deleteById(id);

            userRepository.deleteById(id);
        }
    }
}
