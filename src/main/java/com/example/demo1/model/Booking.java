
package com.example.demo1.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceName;
    private String caregiverName;
    private LocalDate bookingDate;
    private String status;
    private String patientEmail;
    private Long caregiverId;
    
    // Default Constructor
    public Booking() {}

    // Parameterized Constructor
    public Booking(Long id, Long caregiverId, String serviceName, String caregiverName, LocalDate bookingDate, String status, String patientEmail) {
        this.caregiverId = caregiverId;
        this.serviceName = serviceName;
        this.caregiverName = caregiverName;
        this.bookingDate = bookingDate;
        this.status = status;
        this.patientEmail = patientEmail;
        this.id = id;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getCaregiverName() {
        return caregiverName;
    }

    public void setCaregiverName(String caregiverName) {
        this.caregiverName = caregiverName;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }
    public Long getCaregiverId() {
    	return caregiverId;
    }
    public void setCaregiverId(Long caregiverId) {
    	this.caregiverId = caregiverId;
    }
}