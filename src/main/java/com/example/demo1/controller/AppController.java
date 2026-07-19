package com.example.demo1.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import com.example.demo1.model.Patient;
import com.example.demo1.model.Booking;
import com.example.demo1.repository.BookingRepository;
import com.example.demo1.repository.PatientRepository;


@Controller
public class AppController {

	 @Autowired
	    private PatientRepository patientRepository;

    
    @Autowired
    private BookingRepository bookingRepository;

    // --- LOGIN LOGIC ---
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginPatient(@RequestParam String email, 
                            @RequestParam String password, 
                            HttpSession session) {
        List<Patient> patients = patientRepository.findByEmail(email);
        if ( patients != null && ! patients.isEmpty()){
        	Patient patient =  patients.get(0);
            session.setAttribute("patientId", patient.getId());
            session.setAttribute("email", email);
            return "redirect:/dashboard";
        }
        return "redirect:/login?error=true";
    }

    // --- DASHBOARD LOGIC ---
    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        // 1. Session se email uthayein
        String email = (String) session.getAttribute("email");
        Long patientId = (Long) session.getAttribute("patientId");
        
        if (email == null || patientId == null) {
            return "redirect:/login"; // Agar session expire ho gaya hai, wapas login bhejein
        }

        // 2. Database se user data layein
        List<Patient> patients = patientRepository.findByEmail(email);
        if (patients!= null && !patients.isEmpty()) {
            Patient loggedInPatient=patients.get(0);
            
            // 3. IMPORTANT: Session mein pura object daalein taaki HTML mein 'session.user' kaam kare
            session.setAttribute("patient", loggedInPatient); 
            
            // 4. (Optional) Model mein bhi daalein taaki sirf 'user.name' use kar sakein
            model.addAttribute("patient", loggedInPatient);
            
            List<Booking> bookings =bookingRepository.findByPatientEmail(email);
            model.addAttribute("bookings", bookings);
            
            return "dashboard";
        }
        return "redirect:/login";
    }

    // --- LOGOUT LOGIC ---
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}