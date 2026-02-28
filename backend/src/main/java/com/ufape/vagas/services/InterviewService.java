package com.ufape.vagas.services;

import com.ufape.vagas.models.Interview;
import com.ufape.vagas.models.InterviewSchedule;
import com.ufape.vagas.repositories.InterviewRepository;
import com.ufape.vagas.repositories.InterviewScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InterviewService {

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private InterviewScheduleRepository interviewScheduleRepository;

    public List<InterviewSchedule> findTop5InterviewSchedules() {
        return interviewScheduleRepository.findTop5Details();
    }

    public List<Interview> findAll() {
        return interviewRepository.findAll();
    }

    public Optional<Interview> findById(Long id) {
        return interviewRepository.findById(id);
    }

    @Transactional
    public Interview save(Interview interview) {
        return interviewRepository.save(interview);
    }

    @Transactional
    public void deleteById(Long id) {
        interviewRepository.deleteById(id);
    }
}