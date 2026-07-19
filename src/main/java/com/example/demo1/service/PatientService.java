package com.example.demo1.service;

import com.example.demo1.model.Patient;
import com.example.demo1.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    // 1. पेशेंट का डेटा सेव या अपडेट करने के लिए
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    // 2. सभी पेशेंट की लिस्ट निकालने के लिए
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // 3. ID के हिसाब से पेशेंट ढूंढने के लिए
    public Patient getPatientById(Long id) {
        Optional<Patient> optional = patientRepository.findById(id);
        return optional.orElse(null); // अगर ID न मिले तो null रिटर्न करेगा
    }

    // 4. ID के हिसाब से पेशेंट डिलीट करने के लिए
    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }
}