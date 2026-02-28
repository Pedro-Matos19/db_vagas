package com.ufape.vagas.services;

import com.ufape.vagas.models.Application;
import com.ufape.vagas.models.DetailsApplication;
import com.ufape.vagas.models.Interview;
import com.ufape.vagas.repositories.ApplicationRepository;
import com.ufape.vagas.repositories.DetailsApplicationRepository;
import com.ufape.vagas.repositories.InterviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private DetailsApplicationRepository detailsApplicationRepository;

    public List<DetailsApplication> findTop5DetailsApplications() {
        return detailsApplicationRepository.findTop5Details();
    }

    public List<Application> findAll() {
        return applicationRepository.findAll();
    }

    public Optional<Application> findById(Long id) {
        return applicationRepository.findById(id);
    }

    @Transactional
    public Application save(Application application) {
        return applicationRepository.save(application);
    }

    @Transactional
    public void deleteById(Long id) {
        List<Interview> interviews = interviewRepository.findByApplicationId(id);
        interviewRepository.deleteAll(interviews);

        applicationRepository.deleteById(id);
    }
}
