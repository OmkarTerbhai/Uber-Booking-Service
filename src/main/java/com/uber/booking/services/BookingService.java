package com.uber.booking.services;

import com.uber.booking.dto.CreateBookingDTO;
import com.uber.booking.dto.UpdateBookingDTO;
import com.uber.common.entities.Booking;

public interface BookingService {

    public Booking createBooking(CreateBookingDTO dto);

    public Booking updateBooking(UpdateBookingDTO dto);
}
