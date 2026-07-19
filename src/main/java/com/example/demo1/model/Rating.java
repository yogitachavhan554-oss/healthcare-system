package com.example.demo1.model;

import jakarta.persistence.*;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int score; // 1 se 5 tak
    private String comments;

    @ManyToOne
    @JoinColumn(name = "caregiver_id")
    private Caregiver caregiver; 
    // Rating kis caregiver ki hai

    // Getters and Setters
    public void setCaregiver(Caregiver caregiver) {
    	this.caregiver = caregiver;
    }
    public void setScore(int score) {
    	this.score = score;
    }
    public void setComments(String comments) {
    	this.comments = comments;
    }
}