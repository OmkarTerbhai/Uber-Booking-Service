package com.uber.booking.services;

import com.uber.booking.dto.CreateBookingDTO;
import com.uber.booking.dto.EndBookingDTO;
import com.uber.booking.dto.UpdateBookingDTO;
import com.uber.common.entities.Booking;

import java.util.List;

public interface BookingService {

    public Booking createBooking(CreateBookingDTO dto);

    public Booking updateBooking(UpdateBookingDTO dto);

    public Boolean endBooking(EndBookingDTO dto);

    public List<List<Booking>> findBookings(String driverIds);
}
