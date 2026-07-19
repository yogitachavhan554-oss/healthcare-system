package com.example.demo1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo1.model.Booking;
import com.example.demo1.repository.BookingRepository;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public void saveBooking(Booking booking) {
        // डिफ़ॉल्ट रूप से बुकिंग का स्टेटस Pending सेट कर रहे हैं
        booking.setStatus("Pending");
        bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        // डेटाबेस से सभी बुकिंग्स की लिस्ट फेच करने के लिए
        return bookingRepository.findAll();
    }
}