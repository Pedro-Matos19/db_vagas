package com.ufape.vagas.services;

import com.ufape.vagas.models.Curriculum;
import com.ufape.vagas.repositories.CurriculumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CurriculumService {

    @Autowired
    private CurriculumRepository curriculumRepository;

    public List<Curriculum> findAll() {
        return curriculumRepository.findAll();
    }

    public Optional<Curriculum> findById(Long id) {
        return curriculumRepository.findById(id);
    }

    @Transactional
    public Curriculum save(Curriculum curriculum) {
        return curriculumRepository.save(curriculum);
    }

    @Transactional
    public void deleteById(Long id) {
        curriculumRepository.deleteById(id);
    }
}