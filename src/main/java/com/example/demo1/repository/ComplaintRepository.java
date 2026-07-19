package com.example.demo1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo1.model.Complaint;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
}