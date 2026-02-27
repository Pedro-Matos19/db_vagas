package com.ufape.vagas.services;

import com.ufape.vagas.models.Phone;
import com.ufape.vagas.repositories.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PhoneService {

    @Autowired
    private PhoneRepository phoneRepository;

    public List<Phone> findAll() {
        return phoneRepository.findAll();
    }

    public Optional<Phone> findById(Long id) {
        return phoneRepository.findById(id);
    }

    @Transactional
    public Phone save(Phone phone) {
        return phoneRepository.save(phone);
    }

    @Transactional
    public void deleteById(Long id) {
        phoneRepository.deleteById(id);
    }
}