package com.uber.booking.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uber.booking.dto.TestRequestDTO;
import com.uber.booking.dto.UpdateBookingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MessageReceiver {

    @Autowired
    private BookingService bookingService;

    ObjectMapper mapper = new ObjectMapper();

    @JmsListener(destination = "defaultQ")
    public void consume(String msg) {
        System.out.println("Message from JMS: " + msg);

        try {
            UpdateBookingDTO dto = mapper.readValue(msg, UpdateBookingDTO.class);
            System.out.println(dto);
            this.bookingService.updateBooking(dto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
