package com.ufape.vagas.services;

import com.ufape.vagas.models.Skill;
import com.ufape.vagas.repositories.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    public List<Skill> findAll() {
        return skillRepository.findAll();
    }

    public Optional<Skill> findById(Long id) {
        return skillRepository.findById(id);
    }

    @Transactional
    public Skill save(Skill skill) {
        return skillRepository.save(skill);
    }

    @Transactional
    public void deleteById(Long id) {
        Optional<Skill> skillOpt = skillRepository.findById(id);
        if (skillOpt.isPresent()) {
            Skill skill = skillOpt.get();
            if (skill.getCandidates() != null) {
                skill.getCandidates().forEach(candidate -> candidate.getSkills().remove(skill));
            }
            if (skill.getRequiredInJobs() != null) {
                skill.getRequiredInJobs().forEach(job -> job.getRequiredSkills().remove(skill));
            }
            skillRepository.deleteById(id);
        }
    }
}
