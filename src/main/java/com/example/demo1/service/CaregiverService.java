package com.example.demo1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo1.model.Caregiver;
import com.example.demo1.repository.CaregiverRepository;
import java.util.List;
import java.util.Optional;

@Service
public class CaregiverService {

    @Autowired
    private CaregiverRepository caregiverRepository;

    public List<Caregiver> getAllCaregivers() {
        return caregiverRepository.findAll();
    }
    
    public void saveCaregiver(Caregiver caregiver) {
        caregiverRepository.save(caregiver);
    }

    // HomeController के लिए यह मेथड बहुत जरूरी है:
    public Optional<Caregiver> getCaregiverById(Long id) {
        return caregiverRepository.findById(id);
    }
}