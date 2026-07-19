package com.example.demo1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo1.model.*;
import com.example.demo1.service.*;
import com.example.demo1.repository.BookingRepository;
import com.example.demo1.repository.CaregiverRepository;
import com.example.demo1.repository.PatientRepository;
import com.example.demo1.repository.RatingRepository;
import com.example.demo1.repository.ServiceRepository;

@Controller
public class HomeController {

    @Autowired private ServiceRepository serviceRepository;
    @Autowired private CaregiverService caregiverService;
    @Autowired private CaregiverRepository caregiverRepository;
    @Autowired private RatingRepository ratingRepository;
    @Autowired private PatientRepository patientRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping("/")
    public String home() { return "index"; }

   

    // Baaki sab methods sahi hain...
    @GetMapping("/register")
    public String register() { return "register"; }

    @PostMapping("/register")
    public String registerPatient(@ModelAttribute Patient patient, @RequestParam("confirmPassword") String confirmPassword ) {
    	if (!patient.getPassword().equals(confirmPassword)) {
    		return "redirect:/register?error=passwordMismatch";
    	}
    	patientRepository.save(patient);
        return "redirect:/login"; 
    }

    @GetMapping("/book")
    public String showBookingPage(@RequestParam(value = "serviceName", required = false) String serviceName, Model model) {
        model.addAttribute("selectedService", serviceName);
        model.addAttribute("services", serviceRepository.findAll());
        List<Caregiver> caregivers = caregiverService.getAllCaregivers();
        model.addAttribute("caregivers", caregivers);
        return "booking";
    }  

    @GetMapping("/caregivers")
    public String caregivers(Model model) {
    	List<Caregiver> list = caregiverRepository.findAll();
    	
    	for(Caregiver c : list) {
    		Double avg = ratingRepository.getAverageRating(c.getId());
    		c.setAverageRating(avg != null ? avg : 0.0);
    	}
        model.addAttribute("caregiversList", list);
        return "caregivers";
    }
    
    @GetMapping("/services")
    public String showServices(Model model) {
    	List<Service> services = serviceRepository.findAll();
        model.addAttribute("servicesList", services);
        return "services";
    }
    
    @GetMapping("/admin")
    public String showAdminDashboards(Model model) {
    	model.addAttribute("totalPatients", patientRepository.count());
    	return "admin";
    }
    
    @GetMapping("/admin/verify_caregivers")
    public String showVerifyCaregivers(Model model) {
        // Database se saare caregivers laye
        List<Caregiver> c = caregiverRepository.findAll(); 
        System.out.println("Caregiver count: " + c.size());
        model.addAttribute("caregiversList", c);
        return "verify_caregivers"; // Aapki HTML file ka naam
    }
    
    
    @GetMapping("/admin/manage_users")
     public String showManageUsers(Model model) {
            // Database se saare registered users fetch karein
         List<Patient> totalPatients = patientRepository.findAll();
          model.addAttribute("patientsList", totalPatients);
          return "manage_users"; // manage_users.html file
        }
    
    @GetMapping("/admin/reports")
    public String showReports(Model model) {
        // Database se total bookings count fetch karein
        long totalBookings = bookingRepository.count(); 
        
        // Data ko model mein add karein
        model.addAttribute("totalBookings", totalBookings);
        model.addAttribute("popularService", "Elderly Care"); // Isse database se bhi dynamic kar sakte hain
        
        return "reports"; // reports.html
    }
    @GetMapping("/admin/reject/{id}")
    public String rejectCaregiver(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Caregiver c = caregiverRepository.findById(id).orElse(null);
        if (c != null) {
            c.setStatus("Rejected"); // Status update to Rejected
            caregiverRepository.save(c);
            
            redirectAttributes.addFlashAttribute("message", "Caregiver Rejected!");
        }
        return "redirect:/admin/verify_caregivers";
    }
    @GetMapping("/admin/approve/{id}")
    public String approveCaregiver(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    	Caregiver c = caregiverRepository.findById(id).orElse(null);
    	if(c != null) {
    		c.setStatus("Approved");
    		caregiverRepository.save(c);
    		redirectAttributes.addFlashAttribute("message", "Caregiver Approved!");
    	}
    	return "redirect:/admin/verify_caregivers";
    }
}