package com.example.demo1.repository;

import com.example.demo1.model.Patient;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
	List<Patient> findByEmail(String email);
}