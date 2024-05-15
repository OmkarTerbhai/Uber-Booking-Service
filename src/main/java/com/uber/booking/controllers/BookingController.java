package com.uber.booking.controllers;

import com.uber.booking.dto.CreateBookingDTO;
import com.uber.booking.services.BookingService;
import com.uber.common.entities.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody CreateBookingDTO dto) {
        Booking b = this.bookingService.createBooking(dto);

        return new ResponseEntity<Booking>(b, HttpStatus.CREATED);
    }
}
