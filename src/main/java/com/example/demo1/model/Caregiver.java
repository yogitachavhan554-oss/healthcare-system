package com.example.demo1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "caregivers")
public class Caregiver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String specialization; 
    private String qualification;
    private double rating;
    private String availability;
    private String experience;
    
    
    // Status field add kiya gaya hai
    @Column(name = "status")
    private String status = "Pending"; 
    
    @Transient
    private double averageRating;

    // Constructors
    public Caregiver() {}

    public Caregiver(Long id, String name, String specialization, String qualification, double rating, String availability, String experience, String status, double averageRating) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.qualification = qualification;
        this.rating = rating;
        this.availability = availability;
        this.experience = experience;
        this.status = status;
        this.averageRating = averageRating;
    }

    // Getters aur Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public String getAvailability() { return availability; }
    public void setAvailability(String availability) { this.availability = availability; }
    
    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }
    
    // Status ke Getters aur Setters
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public void setAverageRating(double averageRating) { this.averageRating = averageRating; }
    public double getAverageRating() { return averageRating; }
}