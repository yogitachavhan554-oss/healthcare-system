package com.example.demo1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.example.demo1.model.Patient;
import com.example.demo1.repository.PatientRepository;

import com.example.demo1.model.Caregiver;
import com.example.demo1.repository.CaregiverRepository;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    // App start hote hi dummy data insert karne ke liye
    @Bean
    CommandLineRunner initDatabase(CaregiverRepository caregiverRepository) {
        return args -> {
            if (caregiverRepository.count() == 0) {
                Caregiver c1 = new Caregiver();
                c1.setName("Amit Sharma");
                c1.setSpecialization("Nursing Care");
                c1.setQualification("B.Sc Nursing");
                caregiverRepository.save(c1);

                Caregiver c2 = new Caregiver();
                c2.setName("Priya Verma");
                c2.setSpecialization("Physiotherapy");
                c2.setQualification("MPT");
                caregiverRepository.save(c2);

                Caregiver c3 = new Caregiver();
                c3.setName("Ramesh Kumar");
                c3.setSpecialization("Elderly Attendant");
                c3.setQualification("Certified Assistant");
                caregiverRepository.save(c3);
            }
        };
    }
        
        @Bean
        CommandLineRunner initPatient(PatientRepository patientRepository) {
            return args -> {
                if (patientRepository.count() == 0) {
                    Patient p = new Patient();
                    p.setName("Yogita");
                    p.setAge(65);
                    p.setMedicalCondition("Diabetes");
                    p.setAddress("Nagpur");
                    patientRepository.save(p);
                }
            };
       
    }
}   