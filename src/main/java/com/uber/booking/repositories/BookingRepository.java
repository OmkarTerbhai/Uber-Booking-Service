package com.uber.booking.repositories;

import com.uber.common.entities.Booking;
import com.uber.common.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {

    List<Booking> findByDriverIn(List<Driver> drivers);
}
