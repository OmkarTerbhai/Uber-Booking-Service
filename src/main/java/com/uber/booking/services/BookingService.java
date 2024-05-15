package com.uber.booking.services;

import com.uber.booking.dto.CreateBookingDTO;
import com.uber.common.entities.Booking;

public interface BookingService {

    public Booking createBooking(CreateBookingDTO dto);
}
