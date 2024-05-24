package com.uber.booking.controllers;

import com.uber.booking.dto.CreateBookingDTO;
import com.uber.booking.dto.EndBookingDTO;
import com.uber.booking.dto.UpdateBookingDTO;
import com.uber.booking.services.BookingService;
import com.uber.common.entities.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody CreateBookingDTO dto) {
        Booking b = this.bookingService.createBooking(dto);

        return new ResponseEntity<>(b, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Booking> updateBooking(@RequestBody UpdateBookingDTO dto) {
        Booking b = this.bookingService.updateBooking(dto);

        return new ResponseEntity<>(b, HttpStatus.CREATED);
    }

    @PatchMapping("/end")
    public ResponseEntity<Boolean> endBooking(@RequestBody EndBookingDTO dto) {
        boolean res = this.bookingService.endBooking(dto);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
