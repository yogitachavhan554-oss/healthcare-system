package com.example.demo1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.demo1.model.Complaint;
import com.example.demo1.repository.ComplaintRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ComplaintController {
    @Autowired
    private ComplaintRepository complaintRepository;

    @GetMapping("/complaint")
    public String showComplaintPage() { return "complaint"; }

    @PostMapping("/submit-complaint")
    public String submitComplaint(@ModelAttribute Complaint complaint) {
        complaintRepository.save(complaint);
        return "redirect:/complaint?success";
    }
}