package com.uber.booking.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uber.booking.dto.TestRequestDTO;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MessageReceiver {

    ObjectMapper mapper = new ObjectMapper();

    @JmsListener(destination = "defaultQ")
    public void consume(String msg) {
        System.out.println("Message from JMS: " + msg);

        try {
            TestRequestDTO testRequestDTO = mapper.readValue(msg, TestRequestDTO.class);
            System.out.println(testRequestDTO);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
