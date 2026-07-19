package com.example.demo1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo1.model.Booking;
import com.example.demo1.model.Rating; // Import Rating
import com.example.demo1.model.Caregiver; // Import Caregiver
import com.example.demo1.repository.BookingRepository;
import com.example.demo1.repository.RatingRepository; // Import RatingRepository
import com.example.demo1.repository.CaregiverRepository;
import com.example.demo1.model.Service;
// Import CaregiverRepository
import com.example.demo1.repository.ServiceRepository;

import jakarta.servlet.http.HttpSession;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;
    
    @Autowired
    private RatingRepository ratingRepository; // Naya
    
    @Autowired
    private CaregiverRepository caregiverRepository; // Naya
    
    @Autowired
    private ServiceRepository serviceRepository;

    // ... aapka existing saveBooking method ...

    @PostMapping("/book")
    public String saveBooking(@RequestParam("serviceId") Long serviceId,
                              @RequestParam("caregiverId") Long caregiverId,
                              @RequestParam("bookingDate") LocalDate bookingDate,
                              HttpSession session) {
        String email = (String) session.getAttribute("email"); 
        System.out.println("Debug: Session Email is -> " + email);  // Fixed: session key
        if (email == null) return "redirect:/login";
        
        Optional<Service> serviceOpt = serviceRepository.findById(serviceId);
        Service service = serviceOpt.orElse(null);
        
        Optional<Caregiver> caregiverOpt = caregiverRepository.findById(caregiverId);
        Caregiver caregiver = caregiverOpt.orElse(null);
        
        if (service != null && caregiver != null) {
        Booking booking = new Booking();
        booking.setServiceName(service.getName());
        booking.setCaregiverName(caregiver.getName());
        booking.setBookingDate(bookingDate);
        booking.setPatientEmail(email);
        booking.setStatus("Pending"); // Default status
        
        bookingRepository.save(booking);
        }
        return "redirect:/dashboard";
    }

    // Rating Submit करने के लिए नया Method
    @PostMapping("/submit-rating")
    public String submitRating(@RequestParam Long bookingId, 
                               @RequestParam(required = false)Long caregiverId, 
                               @RequestParam int rating, 
                               @RequestParam String comments) {
    	
    	if (caregiverId == null) {
    		return "redirect:/booking_history?error=caregiverMissing";
    	}
        
        // 1. Caregiver fetch karein
        Caregiver caregiver = caregiverRepository.findById(caregiverId).orElse(null);
        
        // 2. Rating save karein
        Rating newRating = new Rating();
        newRating.setScore(rating);
        newRating.setComments(comments);
        newRating.setCaregiver(caregiver);
        ratingRepository.save(newRating);
        
        // 3. BOOKING STATUS UPDATE KAREIN (Ye zaroori hai!)
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking != null) {
            booking.setStatus("Rated"); // Status change kar diya
            bookingRepository.save(booking); // Database mein save kar diya
        }
        
        return "redirect:/booking-history?success=true";
    }

    @GetMapping("/booking-history")
    public String getBookingHistory(HttpSession session, Model model) {
    	String email = (String) session.getAttribute("email");
    	if (email == null) return "redirect:/login";
        List<Booking> bookings = bookingRepository.findByPatientEmail(email);
        LocalDate today = LocalDate.now();
        for (Booking b : bookings) {
        	if("Pending".equals(b.getStatus()) && (b.getBookingDate().isBefore(today) || b.getBookingDate().isEqual(today))) {
        		b.setStatus("Completed");
        		bookingRepository.save(b);
        	}
        }
        model.addAttribute("bookings", bookings);
        
        return "booking_history"; 
    }
}