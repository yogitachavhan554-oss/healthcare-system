package com.example.demo1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo1.repository.PatientRepository;

import jakarta.servlet.http.HttpSession;

import com.example.demo1.model.Patient;

@Controller
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    // 1. Pehle se bana hua method (Data dikhane ke liye)
    @GetMapping("/patient-profile")
    public String getProfile(Model model, HttpSession session) {
    	Long patientId = (Long) session.getAttribute("patientId");
    	if (patientId == null) {
    		return "redirect:/login";
    	}
    	Patient patient =  patientRepository.findById(patientId).orElse(new Patient());
        model.addAttribute("patient", patient);
        return "patient_profile";
    }

    // 2. Naya method (Edit page load karne ke liye)
    @GetMapping("/patient/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ID"));
        model.addAttribute("patient", patient);
        return "edt_profile"; // Aapki edit_profile.html file
    }

    // 3. Naya method (Edit form submit hone ke baad save karne ke liye)
    @PostMapping("/patient/update")
    public String updatePatient(@ModelAttribute("patient") Patient patient, HttpSession session) {
        // 1. Database se purana patient nikalna zaroori hai
        Patient existingPatient = patientRepository.findById(patient.getId())
                                    .orElseThrow(() -> new IllegalArgumentException("Invalid ID"));

        // 2. Sirf wo fields update karein jo user edit kar sakta hai
        existingPatient.setName(patient.getName());
        existingPatient.setAge(patient.getAge());
        existingPatient.setMobile(patient.getMobile());
        existingPatient.setAddress(patient.getAddress());
        existingPatient.setMedicalCondition(patient.getMedicalCondition());
        // Password ko chhedne ki zaroorat nahi hai (wo existingPatient mein pehle se hai)

        // 3. Save karein
        patientRepository.save(existingPatient);
        
        // 4. Session update karein
        session.setAttribute("patient", existingPatient);
        
        return "redirect:/patient-profile";
    }
}